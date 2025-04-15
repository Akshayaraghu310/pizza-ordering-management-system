package com.example.orderdetails.dto;

public class NotificationDTO {
	private String recipient;
	private String message;
	
	public NotificationDTO(String recipient, String message) {
		super();
		this.recipient = recipient;
		this.message = message;
	}
	
	public NotificationDTO() {
		super();
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

