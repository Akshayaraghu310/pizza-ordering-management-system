
package com.example.orderdetails.service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.orderdetails.client.CustomerDetailsClient;
import com.example.orderdetails.client.NotificationClient;
import com.example.orderdetails.client.PaymentDetailsClient;
import com.example.orderdetails.client.PizzaDetailsClient;
import com.example.orderdetails.config.RabbitMQConfig;
import com.example.orderdetails.dto.ItemsOrderedDTO;
import com.example.orderdetails.dto.NotificationDTO;
import com.example.orderdetails.dto.OrderDetailsDTO;
import com.example.orderdetails.dto.PaymentDetailsReqDTO;
import com.example.orderdetails.dto.PaymentDetailsResDTO;
import com.example.orderdetails.dto.PizzaDetailsDTO;
import com.example.orderdetails.dto.CustomerDetailsDTO;
import com.example.orderdetails.entity.ItemsOrderedEntity;
import com.example.orderdetails.entity.OrderDetailsEntity;
import com.example.orderdetails.repository.ItemsOrderedRepository;
import com.example.orderdetails.repository.OrderDetailsRepository;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {
	
	@Autowired
	private OrderDetailsRepository orderDetailsRepository;
	@Autowired
	private ItemsOrderedRepository itemsOrderedRepository;
	@Autowired
	private PizzaDetailsClient pizzaDetailsClient;
	@Autowired
	private PaymentDetailsClient paymentDetailsClient;
	@Autowired
	private NotificationClient notificationClient;
	@Autowired
	private CustomerDetailsClient customerDetailsClient;
	@Autowired
    private RabbitTemplate rabbitTemplate;
	
	private static final Logger log = LoggerFactory.getLogger(OrderDetailsServiceImpl.class);

	@Override
	public OrderDetailsDTO createOrder(OrderDetailsDTO orderDetailsDTO) {
		PizzaDetailsDTO pizzaDetails=null;
		double totalCost=0.0;
			for(ItemsOrderedDTO item:orderDetailsDTO.getItemsOrdered()) {
			log.info("Check if pizza available");
			/*boolean checkAvailable=pizzaDetailsClient.verifyPizzaAvailability(item.getPizzaId(), item.getQuantity());
			if(!checkAvailable) {
				throw new RuntimeException("InSufficient Pizza quantity: "+ item.getPizzaId());
			}*/
			try {
			pizzaDetails=pizzaDetailsClient.getPizzaDetails(item.getPizzaId());
			if(!(pizzaDetails.getQuantityChange()>=item.getQuantity())) {
				throw new RuntimeException("InSufficient Pizza quantity: "+ item.getPizzaId());
			}
			totalCost+=pizzaDetails.getPrice();
			} catch (Exception e) {
	            log.error("Error checking pizza availability for pizzaId:"+ item.getPizzaId()+ e.getMessage());
	            throw new RuntimeException("Error processing pizza order: " + item.getPizzaId(), e);
	        }
		}
		PaymentDetailsReqDTO paymentReq=new PaymentDetailsReqDTO();
		paymentReq.setCustomerId(orderDetailsDTO.getCustomerId());
		//paymentReq.setTotalAmount(calculateTotalCost(pizzaDetails.getPrice()));
		paymentReq.setTotalAmount(totalCost);
		paymentReq.setCurrency("USD");
		paymentReq.setModeOfPayment(orderDetailsDTO.getModeOfPayment());
		
		log.info("Processing payment");
		ResponseEntity<String> paymentResponse=null;
		try {
		paymentResponse=paymentDetailsClient.processPayment(paymentReq);
		if(paymentResponse.getBody().startsWith("/payment/error")) {
			//throw new RuntimeException("Payment Failed - TransactionId :" + paymentResponse.getTransactionId());
			throw new RuntimeException("Payment Failed -"+ orderDetailsDTO.getOrderId());
		}
		} catch (Exception e) {
	        log.error("Error processing payment for orderId:"+orderDetailsDTO.getOrderId()+e.getMessage());
	        throw new RuntimeException("Payment failed for orderId: " + orderDetailsDTO.getOrderId(), e);
	    }
		orderDetailsDTO.setTotalCost(totalCost);
		OrderDetailsEntity orderDetailsEntity=
			orderDetailsRepository.save(
				convertToEntity(orderDetailsDTO));
		orderDetailsDTO=convertToDTO(orderDetailsEntity);
		orderDetailsDTO.setPaymentResponse(paymentResponse.toString());
		return orderDetailsDTO;
	}
	
	/*@Override
	public PaymentDetailsResDTO completeOrderPayment(String paymentId, String payerId,
			PaymentDetailsReqDTO paymentDetailsReqDTO) {
		
		PaymentDetailsResDTO response=paymentDetailsClient.completePayment(paymentId, payerId, paymentDetailsReqDTO);
		
		if (response.getPaymentStatus().equalsIgnoreCase("Payment Completed")) {
			Optional<OrderDetailsEntity> orderDetailsEntity=orderDetailsRepository.findById(response.getOrderId());
			if(orderDetailsEntity.isPresent()) {
				OrderDetailsEntity entity=orderDetailsEntity.get();
				entity.setStatus("Payment Completed");
				orderDetailsRepository.save(entity);
				
				ResponseEntity<?> customerDto=customerDetailsClient.getCustomerById(paymentDetailsReqDTO.getCustomerId());
				if(null!=customerDto) {
					CustomerDetailsDTO dto=(CustomerDetailsDTO) customerDto.getBody();
				
				notificationClient.sendNotificationEmail(new NotificationDTO(dto.getEmail(),
						"Pizza Ordered SuccessFully! OrderId - " + entity.getOrderId()));
			}
		}
		}
		return response;
	}
	*/
	@Override
	public PaymentDetailsResDTO completeOrderPayment(String paymentId, String payerId,
			OrderDetailsDTO orderDetailsDTO) {
		
		PaymentDetailsResDTO response=paymentDetailsClient.completePayment(paymentId, payerId, orderDetailsDTO);
		log.info("In completeOrderPayment :" + response.getPaymentStatus());
		
		if (response.getPaymentStatus().equalsIgnoreCase("Payment Completed")) {
			OrderDetailsEntity orderEntity=orderDetailsRepository.findByOrderId(response.getOrderId());
			//log.info("OrderDetailsEntity :"+ orderEntity);
			if(null!=orderEntity) {
				log.info("OrderDetailsEntity is present");
				orderEntity.setStatus("Payment Completed");
				orderDetailsRepository.save(orderEntity);
				
				ResponseEntity<?> customerDto=customerDetailsClient.getCustomerById(orderDetailsDTO.getCustomerId());
				log.info(customerDto.toString());
				log.info("Checking customerDto is not null");
				if(null!=customerDto) {
					log.info("Inside customerDto check");
					CustomerDetailsDTO dto=(CustomerDetailsDTO) customerDto.getBody();
					log.info(dto.toString());
				
					NotificationDTO notification = new NotificationDTO(
		                    dto.getEmail(),
		                    "Your payment was successful! Pizza Order ID: " + orderEntity.getOrderId()
		                );

		                // Publish payment success notification to RabbitMQ
					log.info("Preparing to publish message to RabbitMQ...");
					try {
					    rabbitTemplate.convertAndSend(
					        RabbitMQConfig.EXCHANGE, // Exchange
					        "payment.success",      // Routing Key
					        notification            // NotificationDTO as message
					    );
					    log.info("Message published to RabbitMQ for OrderId: " + orderEntity.getOrderId());
					} catch (Exception e) {
					    log.error("Failed to publish message to RabbitMQ for OrderId: " + orderEntity.getOrderId(), e);
					}
					log.info("After publishing message to RabbitMQ...");
		            }
			}
		}
		return response;
		}
	
	
	/*private Double calculateTotalCost(List<ItemsOrderedDTO> itemsOrdered) {
		return itemsOrdered.stream().mapToDouble(i -> i.getPrice()).sum();
	}*/

	@Override
	public OrderDetailsDTO getOrder(Long id) {
		OrderDetailsEntity orderDetailsEntity=orderDetailsRepository.findById(id)
				.orElseThrow(()->new RuntimeException("Order not found"));
		return convertToDTO(orderDetailsEntity);
	}
	
	@Override
	public List<OrderDetailsDTO> getAllOrders() {
		//return orderDetailsRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
        return orderDetailsRepository.findAll().stream().map(odEntityList -> convertToDTO(odEntityList)).
        		collect(Collectors.toList());
	}
	
	@Override
	public OrderDetailsDTO updateOrder(Long id, OrderDetailsDTO orderDetailsDTO) {
		OrderDetailsEntity orderDetailsEntity=orderDetailsRepository.findById(id).
				orElseThrow(() ->new RuntimeException("Order not found"));
		orderDetailsEntity=mapEntity(orderDetailsDTO,orderDetailsEntity);
		orderDetailsEntity.setOrderId(id);
		orderDetailsRepository.save(orderDetailsEntity);
		return convertToDTO(orderDetailsEntity);
	}

	@Override
	public String deleteOrder(Long id) {
		OrderDetailsEntity orderDetailsEntity=orderDetailsRepository.findById(id).
				orElseThrow(()-> new RuntimeException("Order not found"));
		orderDetailsRepository.delete(orderDetailsEntity);
		return "Order deleted successfully!!!";
	}
	
	public OrderDetailsEntity mapEntity(OrderDetailsDTO orderDetailsDTO, OrderDetailsEntity orderDetailsEntity) {
		orderDetailsEntity.setCustomerId(null!=orderDetailsDTO.getCustomerId()?orderDetailsDTO.getCustomerId():orderDetailsEntity.getCustomerId());
		orderDetailsEntity.setStatus(null!=orderDetailsDTO.getStatus()?orderDetailsDTO.getStatus():orderDetailsEntity.getStatus());
		orderDetailsEntity.setOrderDate(null!=orderDetailsDTO.getOrderDate()?orderDetailsDTO.getOrderDate():orderDetailsEntity.getOrderDate());
		orderDetailsEntity.setDeliveryDate(null!=orderDetailsDTO.getDeliveryDate()?orderDetailsDTO.getDeliveryDate():orderDetailsEntity.getDeliveryDate());
		orderDetailsEntity.setDeliveryAddress(null!=orderDetailsDTO.getDeliveryAddress()?orderDetailsDTO.getDeliveryAddress():orderDetailsEntity.getDeliveryAddress());
		
		if(orderDetailsDTO.getItemsOrdered()!=null) {
		List<ItemsOrderedEntity> itemsOrderd=orderDetailsDTO.getItemsOrdered().stream().
				map(orderItems-> {
					ItemsOrderedEntity itemsOrdered=new ItemsOrderedEntity();
					itemsOrdered.setPizzaId(orderItems.getPizzaId());
					itemsOrdered.setOrderDetails(orderDetailsEntity);
					itemsOrdered.setQuantity(orderItems.getQuantity());
					//itemsOrdered.setPrice(orderItems.getPrice());
					return itemsOrdered;
				}).collect(Collectors.toList());
		orderDetailsEntity.setItemsOrdered(itemsOrderd);
		}
		else {
			orderDetailsEntity.setItemsOrdered(orderDetailsEntity.getItemsOrdered());
		}
		return orderDetailsEntity;
	}
	public OrderDetailsDTO convertToDTO(OrderDetailsEntity orderDetailsEntity) {
		List<ItemsOrderedDTO> itemsOrderedDTO=orderDetailsEntity.getItemsOrdered().stream().
				map(itemsOrdered -> new ItemsOrderedDTO(itemsOrdered.geId(), itemsOrdered.getPizzaId(), itemsOrdered.getQuantity())).collect(Collectors.toList());
		return new OrderDetailsDTO(orderDetailsEntity.getOrderId(),orderDetailsEntity.getCustomerId(),itemsOrderedDTO,orderDetailsEntity.getModeOfPayment()," ",
				orderDetailsEntity.getStatus(),orderDetailsEntity.getOrderDate(),orderDetailsEntity.getDeliveryDate(),orderDetailsEntity.getDeliveryAddress(),
				orderDetailsEntity.getTotalCost(),orderDetailsEntity.getCurrency());
	}
		
		
	/*public OrderDetailsDTO convertToDTO(OrderDetailsEntity orderDetailsEntity) {
		List<ItemsOrderedDTO> itemsOrderedDTO=orderDetailsEntity.getItemsOrdered().stream().
				map(itemsOrdered -> new ItemsOrderedDTO(itemsOrdered.geId(), itemsOrdered.getPizzaId(), itemsOrdered.getQuantity())).collect(Collectors.toList());
		return new OrderDetailsDTO(orderDetailsEntity.getOrderId(),orderDetailsEntity.getCustomerId(),itemsOrderedDTO,orderDetailsEntity.getModeOfPayment(),
				orderDetailsEntity.getStatus(),
				orderDetailsEntity.getOrderDate(),orderDetailsEntity.getDeliveryDate(),orderDetailsEntity.getDeliveryAddress());
	}*/

	public OrderDetailsEntity convertToEntity(OrderDetailsDTO orderDetailsDTO) {
		OrderDetailsEntity orderDetailsEntity=new OrderDetailsEntity();
		orderDetailsEntity.setCustomerId(orderDetailsDTO.getCustomerId());
		orderDetailsEntity.setModeOfPayment(orderDetailsDTO.getModeOfPayment());
		orderDetailsEntity.setTotalCost(orderDetailsDTO.getTotalCost());
		orderDetailsEntity.setCurrency(orderDetailsDTO.getCurrency());
		orderDetailsEntity.setStatus(orderDetailsDTO.getStatus());
		orderDetailsEntity.setOrderDate(LocalDateTime.now());
		orderDetailsEntity.setDeliveryDate(LocalDateTime.now().plusMinutes(30));
		orderDetailsEntity.setDeliveryAddress(orderDetailsDTO.getDeliveryAddress());
		
		List<ItemsOrderedEntity> itemsOrderd=orderDetailsDTO.getItemsOrdered().stream().
				map(orderItems-> {
					pizzaDetailsClient.updatePizza(new PizzaDetailsDTO(orderItems.getPizzaId(),-orderItems.getQuantity(),"",0.0));
					
					ItemsOrderedEntity itemsOrdered=new ItemsOrderedEntity();
					itemsOrdered.setPizzaId(orderItems.getPizzaId());
					itemsOrdered.setOrderDetails(orderDetailsEntity);
					itemsOrdered.setQuantity(orderItems.getQuantity());
					//itemsOrdered.setPrice(orderItems.getPrice());
					return itemsOrdered;
				}).collect(Collectors.toList());
		orderDetailsEntity.setItemsOrdered(itemsOrderd);
		return orderDetailsEntity;
	}

	@Override
	public List<OrderDetailsDTO> getOrdersByCustomerId(Long customerId) {
		List<OrderDetailsEntity> orderEntity=orderDetailsRepository.findByCustomerId(customerId);
		if(null!=orderEntity && !orderEntity.isEmpty()) {
		return orderEntity.stream().map(order -> convertToDTO(order)).collect(Collectors.toList());
	}
		return null;
}
	public void sendTestMessage() {
        try {
            rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                "payment.success",
                "Test Message"
            );
            log.info("Test message sent successfully.");
        } catch (Exception e) {
            log.error("Failed to send test message to RabbitMQ", e);
        }
    }
}
