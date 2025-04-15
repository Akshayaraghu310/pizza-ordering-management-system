package com.example.paymentdetails.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "Paypal-PaymentGateway")
public interface PaypalClient {

    @PostMapping("/payment/create")
    public ResponseEntity<String> createPayment(
            @RequestParam("method") String method,
            @RequestParam("amount") String amount,
            @RequestParam("currency") String currency,
            @RequestParam("description") String description
    );

    @GetMapping("/payment/success")
    public ResponseEntity<String> paymentSuccess(
            @RequestParam("paymentId") String paymentId,
            @RequestParam("PayerID") String payerId
    );

    @GetMapping("/payment/cancel")
    public ResponseEntity<String> paymentCancel();

    @GetMapping("/payment/error")
    public ResponseEntity<String> paymentError();
}