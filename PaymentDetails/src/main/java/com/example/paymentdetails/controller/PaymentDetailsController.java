package com.example.paymentdetails.controller;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.example.paymentdetails.dto.OrderDetailsDTO;
import com.example.paymentdetails.dto.PaymentDetailsReqDTO;
import com.example.paymentdetails.dto.PaymentDetailsResDTO;
import com.example.paymentdetails.service.PaymentDetailsService;


@RestController
@RequestMapping("/api/paymentDetails")
public class PaymentDetailsController {
    
    @Autowired
    private PaymentDetailsService paymentDetailsService;
    private static final Logger log = LoggerFactory.getLogger(PaymentDetailsController.class);
    
    @PostMapping("/processPayment")
    public ResponseEntity<String> processPayment(@RequestBody PaymentDetailsReqDTO paymentDetailsReqDTO) {
    	log.info("Inside process payment controller");
        try {
            // Process payment and get redirect URL
            String redirectUrl = paymentDetailsService.processPayment(paymentDetailsReqDTO);
            log.info(redirectUrl);
            
            if (redirectUrl.startsWith("/payment/error")) {
            	log.info("payment error");
                // Return an error response
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing payment. Please try again.");
            }
            
            // Return the redirect URL with a FOUND status
            //return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(redirectUrl)).build();
            return new ResponseEntity<>(redirectUrl,HttpStatus.OK);
        } catch (Exception e) {
            // Log the error and return a generic error message
            log.error("Error processing payment: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }
    
    /*@GetMapping("/completePayment")
    public ResponseEntity<String> completePayment(
            @RequestParam String paymentId, 
            @RequestParam String payerId, 
            @RequestBody PaymentDetailsResDTO paymentDetailsDto) {
        try {
            // Complete the payment and get the response
            String response = paymentDetailsService.completePayment(paymentId, payerId, paymentDetailsDto);
            
            // Return the response based on payment completion
            if (response.equals("approved")) {
                return ResponseEntity.ok("Payment successful.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment failed or not approved.");
            }
        } catch (Exception e) {
            // Log the error and return an error response
            log.error("Error completing payment: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred while completing the payment.");
        }
    }*/
    @PostMapping("/completePayment")
    public PaymentDetailsResDTO completePayment(
            @RequestParam String paymentId, 
            @RequestParam String payerId, 
            @RequestBody OrderDetailsDTO orderDetailsDTO) {
    	log.info("Inside paymentcontroller");
    	PaymentDetailsResDTO response=new PaymentDetailsResDTO();
        try {
            // Complete the payment and get the response
        	response = paymentDetailsService.completePayment(paymentId, payerId, orderDetailsDTO);
            
            // Return the response based on payment completion
        	return response;
        } catch (Exception e) {
            // Log the error and return an error response
            log.error("Error completing payment: ", e);
            throw new RuntimeException("An unexpected error occurred while completing the payment."+ response.getTransactionId());
            //return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred while completing the payment.");
        }
    }
}