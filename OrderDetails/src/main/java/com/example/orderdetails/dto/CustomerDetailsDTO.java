package com.example.orderdetails.dto;

public class CustomerDetailsDTO {
	private Long customerId;
	private String name;
	private String phoneNumber;
	private String email;
	private String address;

	public CustomerDetailsDTO(Long customerId, String name, String phoneNumber, String email, String address) {
		this.customerId = customerId;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.address = address;
	}

	public CustomerDetailsDTO() {
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
