package com.example.customerdetails.service;

import java.util.List;

import com.example.customerdetails.dto.CustomerDetailsDTO;
import com.example.customerdetails.dto.OrderDetailsDTO;

public interface CustomerDetailsService {

	List<OrderDetailsDTO> getCustomerOrders(Long customerId);

	String createCustomer(CustomerDetailsDTO customerDetailsDTO);

	String updateCustomerDetails(CustomerDetailsDTO customerDetailsDto);

	CustomerDetailsDTO getCustomerById(Long customerId);

}
