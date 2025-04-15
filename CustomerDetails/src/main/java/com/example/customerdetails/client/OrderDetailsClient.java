package com.example.customerdetails.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.customerdetails.dto.ItemsOrderedDTO;
import com.example.customerdetails.dto.OrderDetailsDTO;

@FeignClient(name="OrderDetails-service")
public interface OrderDetailsClient {
	
	@GetMapping("/api/orderDetails/getOrdersByCustomerId/{customerId}")
	List<OrderDetailsDTO> getOrdersByCustomerId(@PathVariable("customerId") Long customerId);

}
