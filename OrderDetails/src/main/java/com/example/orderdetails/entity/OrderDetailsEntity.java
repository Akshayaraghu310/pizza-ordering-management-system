package com.example.orderdetails.entity;

import java.time.LocalDateTime;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="orderdetails")
public class OrderDetailsEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderId;
	private Long customerId;
	
	@OneToMany(mappedBy="orderDetails",cascade=CascadeType.ALL)
	private List<ItemsOrderedEntity> itemsOrdered;
	
	private String status;
	private String modeOfPayment;
	private double totalCost;
	private String currency;
	private LocalDateTime orderDate;
	private LocalDateTime deliveryDate;
	private String deliveryAddress;
	
	public OrderDetailsEntity(Long orderId, Long customerId, List<ItemsOrderedEntity> itemsOrdered, String status,
			String modeOfPayment, double totalCost, String currency, LocalDateTime orderDate,
			LocalDateTime deliveryDate, String deliveryAddress) {
		super();
		this.orderId = orderId;
		this.customerId = customerId;
		this.itemsOrdered = itemsOrdered;
		this.status = status;
		this.modeOfPayment = modeOfPayment;
		this.totalCost = totalCost;
		this.currency = currency;
		this.orderDate = orderDate;
		this.deliveryDate = deliveryDate;
		this.deliveryAddress = deliveryAddress;
	}


	public OrderDetailsEntity() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public List<ItemsOrderedEntity> getItemsOrdered() {
		return itemsOrdered;
	}
	public void setItemsOrdered(List<ItemsOrderedEntity> itemsOrdered) {
		this.itemsOrdered = itemsOrdered;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getModeOfPayment() {
		return modeOfPayment;
	}
	public void setModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}
	public double getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
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

	@Override
	public String toString() {
		return "OrderDetailsEntity [orderId=" + orderId + ", customerId=" + customerId + ", itemsOrdered="
				+ itemsOrdered + ", status=" + status + ", modeOfPayment=" + modeOfPayment + ", totalCost=" + totalCost
				+ ", currency=" + currency + ", orderDate=" + orderDate + ", deliveryDate=" + deliveryDate
				+ ", deliveryAddress=" + deliveryAddress + "]";
	}
}
