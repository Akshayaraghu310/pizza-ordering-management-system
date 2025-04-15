package com.example.paymentdetails;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class PaymentDetailsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentDetailsApplication.class, args);
	}

}
