package com.example.orderdetails.health;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import com.example.orderdetails.client.PizzaDetailsClient;

@Component
public class PizzaDetailsServiceHealthCheck implements HealthIndicator {
	
	@Autowired
    private PizzaDetailsClient pizzaDetailsClient;

	@Override
	public Health health() {
		boolean pizzaServiceAvailable = checkPizzaDetailsServiceAvailability();

		if (pizzaServiceAvailable) {
			return Health.up().withDetail("PizzaService", "Available").build();
		} else {
			return Health.down().withDetail("PizzaService", "Not Available").build();
		}
	}
	
	private boolean checkPizzaDetailsServiceAvailability() {
        try {
        	//simply to check whether service is up or not
            pizzaDetailsClient.getPizzaDetails((long) 1025);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
