package com.example.customerdetails.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.customerdetails.client.OrderDetailsClient;
import com.example.customerdetails.controller.CustomerDetailsController;
import com.example.customerdetails.dto.CustomerDetailsDTO;
import com.example.customerdetails.dto.OrderDetailsDTO;
import com.example.customerdetails.entity.CustomerDetailsEntity;
import com.example.customerdetails.repository.CustomerDetailsRepository;

@Service
public class CustomerDetailsServiceImpl implements CustomerDetailsService {
	@Autowired
	public OrderDetailsClient orderDetailsClient;
	@Autowired
	public CustomerDetailsRepository customerDetailsRepository;
	
	private static final Logger log = LoggerFactory.getLogger(CustomerDetailsController.class);

	@Override
	public List<OrderDetailsDTO> getCustomerOrders(Long customerId) {
		List<OrderDetailsDTO> orderDetails=orderDetailsClient.getOrdersByCustomerId(customerId);
		return orderDetails;
	}

	@Override
	public String createCustomer(CustomerDetailsDTO customerDetailsDTO) {
		CustomerDetailsEntity entity=convertToEntity(customerDetailsDTO);
		customerDetailsRepository.save(entity);
		return "New Customer created successfully!!";
	}

	public CustomerDetailsEntity convertToEntity(CustomerDetailsDTO customerDetailsDTO) {
		return new CustomerDetailsEntity(customerDetailsDTO.getCustomerId(),customerDetailsDTO.getName(),customerDetailsDTO.getPhoneNumber(),
				customerDetailsDTO.getEmail(),customerDetailsDTO.getAddress());
	}

	@Override
	public String updateCustomerDetails(CustomerDetailsDTO customerDetailsDto) {
		CustomerDetailsEntity customerEntity=customerDetailsRepository.findById(customerDetailsDto.getCustomerId()).
				orElseThrow(() ->new RuntimeException("CustomerDetails entity not found"));
		customerEntity=mapToEntity(customerDetailsDto,customerEntity);
		customerDetailsRepository.save(customerEntity);
		return "Customer details updated successfully!!";
	}

	public CustomerDetailsEntity mapToEntity(CustomerDetailsDTO customerDetailsDto,
			CustomerDetailsEntity customerEntity) {
		customerEntity.setName(null!=customerDetailsDto.getName()?customerDetailsDto.getName():customerEntity.getName());
		customerEntity.setEmail(null!=customerDetailsDto.getEmail()?customerDetailsDto.getEmail():customerEntity.getEmail());
		customerEntity.setPhoneNumber(null!=customerDetailsDto.getPhoneNumber()?customerDetailsDto.getPhoneNumber():customerEntity.getPhoneNumber());
		customerEntity.setAddress(null!=customerDetailsDto.getAddress()?customerDetailsDto.getAddress():customerEntity.getAddress());
		return customerEntity;
	}

	@Override
	public CustomerDetailsDTO getCustomerById(Long customerId) {
		Optional<CustomerDetailsEntity> entity=customerDetailsRepository.findById(customerId);
		if(entity.isPresent()) {
			CustomerDetailsEntity customerEntity=entity.get();
			return convertToDto(customerEntity);
		}
		return null;
	}

	private CustomerDetailsDTO convertToDto(CustomerDetailsEntity customerEntity) {
		return new CustomerDetailsDTO(customerEntity.getCustomerId(),customerEntity.getName(),customerEntity.getPhoneNumber(),
				customerEntity.getEmail(),customerEntity.getAddress());
	}
}
