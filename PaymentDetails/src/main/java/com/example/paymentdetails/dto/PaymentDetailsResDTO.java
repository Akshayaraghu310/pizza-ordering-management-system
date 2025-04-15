package com.example.paymentdetails.dto;

import java.time.LocalDateTime;

public class PaymentDetailsResDTO {
	private Long orderId;
	private String transactionId;
	private Double totalAmount;
	private String paymentStatus;
	private LocalDateTime paymentDate;
	
	public PaymentDetailsResDTO() {
		
	}
	public PaymentDetailsResDTO(Long orderId,String transactionId, Double totalAmount, String paymentStatus,
			LocalDateTime paymentDate) {
		super();
		this.orderId = orderId;
		this.transactionId=transactionId;
		this.totalAmount = totalAmount;
		this.paymentStatus = paymentStatus;
		this.paymentDate = paymentDate;
	}
	
	public Long getOrderId() {
		return orderId;
	}
	
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public LocalDateTime getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(LocalDateTime paymentDate) {
		this.paymentDate = paymentDate;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	@Override
	public String toString() {
		return "PaymentDetailsResDTO [orderId=" + orderId + ", totalAmount=" + totalAmount + ", paymentStatus="
				+ paymentStatus + ", paymentDate=" + paymentDate + ", transactionId=" + transactionId + "]";
	}
}
