package com.example.customerdetails.controller;

import java.util.List;

import org.springframework.core.env.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.customerdetails.dto.CustomerDetailsDTO;
import com.example.customerdetails.dto.OrderDetailsDTO;
import com.example.customerdetails.service.CustomerDetailsService;

@RestController
@RequestMapping("/api/customerDetails")
public class CustomerDetailsController {
	@Autowired
	private CustomerDetailsService customerDetailsService;
	
	private static final Logger log = LoggerFactory.getLogger(CustomerDetailsController.class);
	
	@Autowired
    private Environment environment;
	
	@PostMapping("/createCustomer") 
	public ResponseEntity<?> createCustomer(@RequestBody CustomerDetailsDTO customerDetailsDTO) {
		String response=customerDetailsService.createCustomer(customerDetailsDTO);
		log.info(environment.getProperty("local.server.port"));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/getCustomerOrders/{customerId}")
	public ResponseEntity<?> getCustomerOrders(@PathVariable("customerId") Long customerId) {
		List<OrderDetailsDTO> response=customerDetailsService.getCustomerOrders(customerId);
		if(null!=response && !response.isEmpty()) {
			return new ResponseEntity<>(response,HttpStatus.OK);
		}
		return new ResponseEntity<>("No Orders available for this customerId : "+customerId,HttpStatus.EXPECTATION_FAILED);
	}
	
	@PutMapping("/updateCustomer")
	public ResponseEntity<?> updateCustomerDetails(@RequestBody CustomerDetailsDTO customerDetailsDto) {
		try {
		String response=customerDetailsService.updateCustomerDetails(customerDetailsDto);
		return new ResponseEntity<>(response,HttpStatus.OK);
		}
		catch(RuntimeException e){
			return new ResponseEntity<>(e.getMessage(),HttpStatus.EXPECTATION_FAILED);
		}
	}
	@GetMapping("/getCustomerInfo/{customerId}") 
	public ResponseEntity<CustomerDetailsDTO> getCustomerById(@PathVariable Long customerId) {
		CustomerDetailsDTO response=customerDetailsService.getCustomerById(customerId);
		if(null!=response) {
			return ResponseEntity.ok(response);
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Customer found");
	}
}
