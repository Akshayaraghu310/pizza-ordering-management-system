package com.example.paymentdetails.service;

import org.springframework.web.servlet.view.RedirectView;

import com.example.paymentdetails.dto.OrderDetailsDTO;
import com.example.paymentdetails.dto.PaymentDetailsReqDTO;
import com.example.paymentdetails.dto.PaymentDetailsResDTO;
import com.example.paymentdetails.entity.PaymentDetailsEntity;

public interface PaymentDetailsService {

	String processPayment(PaymentDetailsReqDTO paymentDetailsReqDTO);

	PaymentDetailsResDTO completePayment(String paymentId, String payerId, OrderDetailsDTO orderDetailsDTO);
	String cancelPayment();
	String errorPayment();

}
