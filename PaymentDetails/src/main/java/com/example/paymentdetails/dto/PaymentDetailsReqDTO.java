package com.example.paymentdetails.dto;

public class PaymentDetailsReqDTO {
	private Long customerId;
	private Double totalAmount;
	private String currency;
	private String modeOfPayment;
	
	public PaymentDetailsReqDTO() {
		
	}

	public PaymentDetailsReqDTO(Long customerId, Double totalAmount, String currency, String modeOfPayment) {
		super();
		this.customerId = customerId;
		this.totalAmount = totalAmount;
		this.currency = currency;
		this.modeOfPayment = modeOfPayment;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getModeOfPayment() {
		return modeOfPayment;
	}

	public void setModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}

	@Override
	public String toString() {
		return "PaymentDetailsReqDTO [customerId=" + customerId + ", totalAmount=" + totalAmount + ", currency=" + currency
				+ ", modeOfPayment=" + modeOfPayment + "]";
	}
}
