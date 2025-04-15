package com.example.customerdetails.dto;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDetailsDTO {
	private Long orderId;
	private Long customerId;
	private List<ItemsOrderedDTO> itemsOrdered;
	private String modeOfPayment;
	private String paymentResponse;
	private String status;
	private LocalDateTime orderDate;
	private LocalDateTime deliveryDate;
	private String deliveryAddress;
	
	public OrderDetailsDTO(Long orderId, Long customerId,
			List<ItemsOrderedDTO> itemsOrdered, String modeOfPayment, String paymentResponse, String status,LocalDateTime orderDate,
			LocalDateTime deliveryDate, String deliveryAddress) {
		super();
		this.orderId=orderId;
		this.customerId = customerId;
		this.itemsOrdered = itemsOrdered;
		this.modeOfPayment = modeOfPayment;
		this.paymentResponse= paymentResponse;
		this.status=status;
		this.orderDate = orderDate;
		this.deliveryDate = deliveryDate;
		this.deliveryAddress = deliveryAddress;
	}
	
	public OrderDetailsDTO() {
		super();
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public List<ItemsOrderedDTO> getItemsOrdered() {
		return itemsOrdered;
	}

	public void setItemsOrdered(List<ItemsOrderedDTO> itemsOrdered) {
		this.itemsOrdered = itemsOrdered;
	}

	public String getModeOfPayment() {
		return modeOfPayment;
	}

	public void setModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public LocalDateTime getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(LocalDateTime deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public String getPaymentResponse() {
		return paymentResponse;
	}

	public void setPaymentResponse(String paymentResponse) {
		this.paymentResponse = paymentResponse;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	@Override
	public String toString() {
		return "OrderDetailsDTO [orderId=" + orderId + ", customerId=" + customerId + ", itemsOrdered=" + itemsOrdered
				+ ", modeOfPayment=" + modeOfPayment + ", paymentResponse=" + paymentResponse + ", status=" + status
				+ ", orderDate=" + orderDate + ", deliveryDate=" + deliveryDate + ", deliveryAddress=" + deliveryAddress
				+ "]";
	}

	
}
