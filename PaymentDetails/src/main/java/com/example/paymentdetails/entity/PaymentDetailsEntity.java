package com.example.paymentdetails.entity;


import java.time.LocalDateTime;
import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="paymentdetails")
public class PaymentDetailsEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderId;
	
	@Column(nullable = false, unique = true)
    private String transactionId;
	
	private Double totalAmount;
	private String paymentStatus;
	private LocalDateTime paymentDate;
	
	public PaymentDetailsEntity() {
        this.transactionId = UUID.randomUUID().toString();
    }

	public PaymentDetailsEntity(String transactionId, Long orderId, Double totalAmount, String paymentStatus,
			LocalDateTime paymentDate) {
		super();
		this.orderId = orderId;
		this.transactionId = transactionId;
		this.totalAmount = totalAmount;
		this.paymentStatus = paymentStatus;
		this.paymentDate = paymentDate;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
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

	@Override
	public String toString() {
		return "PaymentDetailsEntity [transactionId=" + transactionId + ", orderId=" + orderId + ", totalAmount="
				+ totalAmount + ", paymentStatus=" + paymentStatus + ", paymentDate=" + paymentDate + "]";
	}
}