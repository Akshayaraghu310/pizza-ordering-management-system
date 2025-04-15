package com.example.pizzadetails.service;

import com.example.pizzadetails.dto.PizzaDetailsDTO;

public interface PizzaDetailsService {

	//boolean verifyPizzaAvailability(Long pizzaId, int quantity);

	String updatePizza(PizzaDetailsDTO pizzaDetailsDTO);

	String addNewPizza(PizzaDetailsDTO pizzaDetailsDTO);

	PizzaDetailsDTO getPizzaDetails(Long pizzaId);

}
