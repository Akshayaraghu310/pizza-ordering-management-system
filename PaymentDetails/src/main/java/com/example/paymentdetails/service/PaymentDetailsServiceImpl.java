package com.example.paymentdetails.service;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

import com.example.paymentdetails.client.PaypalClient;
import com.example.paymentdetails.controller.PaymentDetailsController;
import com.example.paymentdetails.dto.OrderDetailsDTO;
import com.example.paymentdetails.dto.PaymentDetailsReqDTO;
import com.example.paymentdetails.dto.PaymentDetailsResDTO;
import com.example.paymentdetails.entity.PaymentDetailsEntity;
import com.example.paymentdetails.repository.PaymentDetailsRepository;

@Service
public class PaymentDetailsServiceImpl implements PaymentDetailsService {

    @Autowired
    private PaypalClient paypalClient;

    @Autowired
    private PaymentDetailsRepository paymentDetailsRepo;
    private static final Logger log = LoggerFactory.getLogger(PaymentDetailsController.class);

    @Override
    public String processPayment(PaymentDetailsReqDTO paymentDetailsReqDTO) {
    	log.info("Payment processing started");
        if ("ONLINE".equals(paymentDetailsReqDTO.getModeOfPayment())) {
        	log.info("Paypal client method calling");
            ResponseEntity<String> response = paypalClient.createPayment(
                    "PAYPAL",
                    paymentDetailsReqDTO.getTotalAmount().toString(),
                    paymentDetailsReqDTO.getCurrency(),
                    "Payment for CustomerID: " + paymentDetailsReqDTO.getCustomerId().toString()
            );
            log.info("Response Status Code: {}", response.getStatusCode());
            log.info("Response Body: {}", response.getBody());
            
            if (response.getStatusCode().is2xxSuccessful()) {
            	log.info("Payment processing Successfull");
                String redirectUrl = response.getBody();
                if (redirectUrl.startsWith("/payment/error")) {
                    return "/payment/error";
                }
                return redirectUrl;
            } else {
                return "/payment/error";
            }
        } else if ("CASH ON DELIVERY".equals(paymentDetailsReqDTO.getModeOfPayment())) {
            return "/cash-on-delivery-confirmation";
        } else {
            return "/payment/error";
        }
    }

    @Override
    public PaymentDetailsResDTO completePayment(String paymentId, String payerId, OrderDetailsDTO orderDetailsDTO) {
    	log.info("Inside paymentDetails completePayment service");
        ResponseEntity<String> response = paypalClient.paymentSuccess(paymentId, payerId);
        PaymentDetailsEntity paymentDetails = new PaymentDetailsEntity();
        log.info(response.getStatusCode().toString());
        log.info(response.getBody());
        if (response.getStatusCode().is2xxSuccessful()) {
        	log.info("payment completion success response");
            paymentDetails.setOrderId(orderDetailsDTO.getOrderId());
            paymentDetails.setPaymentStatus("Payment Completed");
            paymentDetails.setTotalAmount(orderDetailsDTO.getTotalCost());
            paymentDetails.setPaymentDate(LocalDateTime.now());
            paymentDetailsRepo.save(paymentDetails);
            
            return new PaymentDetailsResDTO(paymentDetails.getOrderId(), paymentDetails.getTransactionId(), paymentDetails.getTotalAmount(),
            		paymentDetails.getPaymentStatus(), paymentDetails.getPaymentDate());
        } else {
            throw new RuntimeException("payment failed: "+ paymentDetails.getTransactionId());
        }
    }

    @Override
    public String cancelPayment() {
        ResponseEntity<String> response = paypalClient.paymentCancel();
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            return "Cancellation failed.";
        }
    }

    @Override
    public String errorPayment() {
        ResponseEntity<String> response = paypalClient.paymentError();
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            return "Error page retrieval failed.";
        }
    }
}