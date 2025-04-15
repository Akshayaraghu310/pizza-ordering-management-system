package com.example.orderdetails.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.orderdetails.dto.OrderDetailsDTO;
import com.example.orderdetails.dto.PaymentDetailsReqDTO;
import com.example.orderdetails.dto.PaymentDetailsResDTO;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@FeignClient(name="PaymentDetails-service")
public interface PaymentDetailsClient {
	
	@CircuitBreaker(name = "paymentDetailsClient", fallbackMethod = "processPaymentFallback")
	@PostMapping("/api/paymentDetails/processPayment")
    public ResponseEntity<String> processPayment(@RequestBody PaymentDetailsReqDTO paymentDetailsReqDTO);
	
	@PostMapping("/api/paymentDetails/completePayment")
    public PaymentDetailsResDTO completePayment(
            @RequestParam String paymentId, 
            @RequestParam String payerId, 
            @RequestBody OrderDetailsDTO orderDetailsDTO);
	
	default ResponseEntity<String> processPaymentFallback(PaymentDetailsReqDTO paymentDetailsReqDTO, Throwable throwable) {
        System.out.println("Error calling payment service:"+throwable.getMessage());
        //return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Payment service unavailable.");
        return new ResponseEntity<>("Payment service is currently unavailable. Using fallback mechanism.",
        		HttpStatus.EXPECTATION_FAILED);
    }
}
