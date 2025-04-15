package com.example.paypalintegrationapplication.paypal;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class PaypalController {


	@Autowired
	private PaypalService paypalService;

	private static final Logger log = LoggerFactory.getLogger(PaypalController.class);

	@GetMapping("/")
	public String home() {
		return "index";
	}

	@PostMapping("/payment/create")
	public ResponseEntity<String> createPayment(
			@RequestParam("method") String method,
			@RequestParam("amount") String amount,
			@RequestParam("currency") String currency,
			@RequestParam("description") String description
			) {
		log.info("Inside create payment method in Paypal microservice");
		try {
			String cancelUrl = "http://localhost:8080/payment/cancel";
			String successUrl = "http://localhost:8080/payment/success";

			String approvalUrl = paypalService.createPayment(
					Double.valueOf(amount),
					currency,
					method,
					"sale",
					description,
					cancelUrl,
					successUrl
					);
			log.info("Approval URL: {}", approvalUrl);
			//return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(approvalUrl)).build();
			return ResponseEntity.ok(approvalUrl);

		} catch (PayPalRESTException e) {
			log.error("Error occurred during payment creation: ", e);
			// Return an error URL or message
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("/payment/error");
		}
	}

	@GetMapping("/payment/success")
	public String paymentSuccess(
			@RequestParam("paymentId") String paymentId,
			@RequestParam("PayerID") String payerId
			) {
		try {
			Payment payment = paypalService.executePayment(paymentId, payerId);
			if (payment.getState().equals("approved")) {
				return "paymentSuccess";
				//return ResponseEntity.ok("Payment was successful!");
			} else {
				return "paymentError";
			}
		} catch (PayPalRESTException e) {
			log.error("Error occurred during payment execution: ", e);
			return "paymentError";
		}
	}

		@GetMapping("/payment/cancel")
		public String paymentCancel() {
			return "paymentCancel";
		}

		@GetMapping("/payment/error")
		public String paymentError() {
			return "paymentError";
		}
	}
