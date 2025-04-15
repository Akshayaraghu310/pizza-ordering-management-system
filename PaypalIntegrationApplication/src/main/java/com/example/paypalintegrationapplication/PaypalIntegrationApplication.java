package com.example.paypalintegrationapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PaypalIntegrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaypalIntegrationApplication.class, args);
	}

}
