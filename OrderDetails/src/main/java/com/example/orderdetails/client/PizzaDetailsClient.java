package com.example.orderdetails.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.orderdetails.dto.PizzaDetailsDTO;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@FeignClient(name="PizzaDetails-service")
public interface PizzaDetailsClient {
	
	/*@GetMapping("/api/pizzaDetails/pizzaAvailability/{pizzaId}/{quantity}")
	public boolean verifyPizzaAvailability(@PathVariable("pizzaId") Long pizzaId, @PathVariable("quantity") int quantity );*/
	
	@CircuitBreaker(name = "pizzaDetailsClient", fallbackMethod = "pizzaDetailsFallback")
	@GetMapping("/api/pizzaDetails/pizzaAvailability/{pizzaId}")
	public PizzaDetailsDTO getPizzaDetails(@PathVariable Long pizzaId);
	
	@PostMapping("/api/pizzaDetails/updatePizza")
	public String updatePizza(@RequestBody PizzaDetailsDTO pizzaDetailsDTO);
	
	default PizzaDetailsDTO pizzaDetailsFallback(Long pizzaId, Throwable throwable) {
		System.out.println("Error calling pizza service for pizzaId " + pizzaId + ": " + throwable.getMessage());
        return new PizzaDetailsDTO(); // Return a default or fallback response
    }
}
