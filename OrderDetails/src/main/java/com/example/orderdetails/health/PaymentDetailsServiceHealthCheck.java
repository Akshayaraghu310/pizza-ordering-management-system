package com.example.orderdetails.health;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.example.orderdetails.client.PaymentDetailsClient;
import com.example.orderdetails.dto.OrderDetailsDTO;
import com.example.orderdetails.dto.PaymentDetailsReqDTO;
import com.example.orderdetails.dto.PaymentDetailsResDTO;

@Component
public class PaymentDetailsServiceHealthCheck implements HealthIndicator {
	
	@Autowired
	private PaymentDetailsClient paymentDetailsClient;

	@Override
	public Health health() {
		boolean PaymentDetailsServiceAvailable=checkPaymentServiceAvailable();
		if (PaymentDetailsServiceAvailable) {
			return Health.up().withDetail("PaymentDetailsService", "Available").build();
		}
		else {
			return Health.down().withDetail("PaymentDetailsService", "NotAvailable").build();
		}
	}
	
	public boolean checkPaymentServiceAvailable() {
		try {
			PaymentDetailsResDTO response=paymentDetailsClient.completePayment("bvfgcxd", "abcdfgvh", new OrderDetailsDTO());  
			return null!=response;
		}
		catch (Exception e) {
			return false;
		}
	}
}
