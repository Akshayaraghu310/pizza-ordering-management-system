package com.example.orderdetails.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.orderdetails.dto.CustomerDetailsDTO;

@FeignClient(name="CustomerDetails-service")
public interface CustomerDetailsClient {
	
	@GetMapping("/api/customerDetails/getCustomerInfo/{customerId}") 
	public ResponseEntity<CustomerDetailsDTO> getCustomerById(@PathVariable Long customerId);

}
