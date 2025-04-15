package com.example.orderdetails.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.orderdetails.dto.ItemsOrderedDTO;
import com.example.orderdetails.dto.OrderDetailsDTO;
import com.example.orderdetails.dto.PaymentDetailsReqDTO;
import com.example.orderdetails.dto.PaymentDetailsResDTO;
import com.example.orderdetails.service.OrderDetailsService;


@RestController
@RequestMapping("/api/orderDetails")
public class OrderDetailsController {
	
	@Autowired
	private OrderDetailsService orderDetailsService;
	
	@PostMapping("/createOrder")
	public ResponseEntity<?> createOrder(@RequestBody OrderDetailsDTO orderDetailsDTO) {
		try {
			OrderDetailsDTO response=orderDetailsService.createOrder(orderDetailsDTO);
			return new ResponseEntity<>(response,HttpStatus.OK);
		}
		catch(RuntimeException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.EXPECTATION_FAILED);
		}
	}
	@PostMapping("/completeOrderPayment")
	public ResponseEntity<String> completeOrder(@RequestParam String paymentId, 
            @RequestParam String PayerID, @RequestBody OrderDetailsDTO orderDetailsDTO) {
		try {
			PaymentDetailsResDTO response=orderDetailsService.completeOrderPayment(paymentId, PayerID, orderDetailsDTO);
			return new ResponseEntity<>(response + "Order Placed Successfully!!",HttpStatus.OK);
		}
		catch(RuntimeException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	@GetMapping("/getOrder/{id}")
	public ResponseEntity<?> getOrder(@PathVariable Long id) {
		OrderDetailsDTO response=orderDetailsService.getOrder(id);
		return new ResponseEntity<>(response + "\nOrder Fetched Successfully!!!",HttpStatus.OK);
	}
	
	@GetMapping("/getAllOrders")
	public ResponseEntity<?> getAllOrders() {
		List<OrderDetailsDTO> response=orderDetailsService.getAllOrders();
		return new ResponseEntity<>(response + "\nAll Orders fetched Successfully!!!", HttpStatus.OK);
	}
	
	@PutMapping("/updateOrder/{id}")
	public ResponseEntity<?> updateOrder(@PathVariable Long id, @RequestBody OrderDetailsDTO orderDetailsDTO) {
		OrderDetailsDTO response=orderDetailsService.updateOrder(id, orderDetailsDTO);
		return new ResponseEntity<>(response + "\nOrderDetails updated Sucessfully!!!", HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteOrder/{id}")
	public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
		String response=orderDetailsService.deleteOrder(id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	@GetMapping("/getOrdersByCustomerId/{customerId}")
	public ResponseEntity<?> getOrdersByCustomerId(@PathVariable("customerId") Long customerId) {
		List<OrderDetailsDTO> response=orderDetailsService.getOrdersByCustomerId(customerId);
		if(null!=response) {
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		return ResponseEntity.noContent().build();
	}
	@GetMapping("/send-test-message")
    public ResponseEntity<String> sendTestMessage() {
        orderDetailsService.sendTestMessage();
        return ResponseEntity.ok("Test message sent.");
    }
}
