package com.example.orderdetails.service;

import java.util.List;

import com.example.orderdetails.dto.ItemsOrderedDTO;
import com.example.orderdetails.dto.OrderDetailsDTO;
import com.example.orderdetails.dto.PaymentDetailsReqDTO;
import com.example.orderdetails.dto.PaymentDetailsResDTO;

public interface OrderDetailsService {

	OrderDetailsDTO createOrder(OrderDetailsDTO orderDetailsDTO);

	OrderDetailsDTO getOrder(Long id);

	List<OrderDetailsDTO> getAllOrders();

	OrderDetailsDTO updateOrder(Long id, OrderDetailsDTO orderDetailsDTO);

	String deleteOrder(Long id);

	PaymentDetailsResDTO completeOrderPayment(String paymentId, String payerId, OrderDetailsDTO orderDetailsDTO);

	List<OrderDetailsDTO> getOrdersByCustomerId(Long customerId);

	void sendTestMessage();

}
