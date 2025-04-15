package com.example.customerdetails.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name="customerdetails")
public class CustomerDetailsEntity {
	
	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE,generator="custom_seq")
	@SequenceGenerator(name="custom_seq", sequenceName = "custom_sequence", initialValue= 101, allocationSize = 1)
	private Long customerId;
	private String name;
	private String phoneNumber;
	private String email;
	private String address;
	
	public CustomerDetailsEntity(Long customerId, String name, String phoneNumber, String email, String address) {
		this.customerId = customerId;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.address = address;
	}
	
	public CustomerDetailsEntity() {
		super();
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "CustomerDetailsEntity [customerId=" + customerId + ", name=" + name + ", phoneNumber=" + phoneNumber + ", email="
				+ email + ", address=" + address + "]";
	}	
}
