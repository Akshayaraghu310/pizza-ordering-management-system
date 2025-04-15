package com.example.pizzadetails.controller;

import org.slf4j.Logger;
import org.springframework.core.env.Environment;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pizzadetails.config.PropertiesConfigServer;
import com.example.pizzadetails.dto.PizzaDetailsDTO;
import com.example.pizzadetails.service.PizzaDetailsService;

@RestController
@RequestMapping("/api/pizzaDetails")
public class PizzaDetailsController {
	
	@Autowired
	private PizzaDetailsService pizzaDetailsService;
	private static final Logger log = LoggerFactory.getLogger(PizzaDetailsController.class);
	@Autowired
    private Environment environment;
	
	@Autowired
	private PropertiesConfigServer propertiesConfigServer;
	
	/*@GetMapping("/pizzaAvailability/{pizzaId}/{quantity}")
	public ResponseEntity<?> verifyPizzaAvailability(@PathVariable Long pizzaId, @PathVariable int quantity) {
		log.info("Inside PizzaDetailsController");
		boolean response=pizzaDetailsService.verifyPizzaAvailability(pizzaId,quantity);
		return new ResponseEntity<>(response,HttpStatus.OK);
		//return ResponseEntity.ok(response);
	}*/
	
	@GetMapping("/pizzaAvailability/{pizzaId}")
	public ResponseEntity<PizzaDetailsDTO> getPizzaDetails(@PathVariable Long pizzaId) {
		log.info("Inside PizzaDetailsController");
		PizzaDetailsDTO response=pizzaDetailsService.getPizzaDetails(pizzaId);
		log.info(environment.getProperty("local.server.port"));
		return new ResponseEntity<>(response,HttpStatus.OK);
		//return ResponseEntity.ok(response);
	}
	
	@PostMapping("/addNewPizza") 
	public ResponseEntity<?> addPizza(@RequestBody PizzaDetailsDTO pizzaDetailsDTO) {
		try {
			String response=pizzaDetailsService.addNewPizza(pizzaDetailsDTO);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		catch(RuntimeException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.EXPECTATION_FAILED);
			
		}
	}
	
	@PostMapping("/updatePizza")
	public ResponseEntity<?> updatePizza(@RequestBody PizzaDetailsDTO pizzaDetailsDTO) {
		String response=pizzaDetailsService.updatePizza(pizzaDetailsDTO);
		return new ResponseEntity<>(response, HttpStatus.OK);
		//return ResponseEntity.ok().build();
	}
	
	// Spring cloud-Config server testing
	@GetMapping("/serviceMessage")
    public ResponseEntity<String> getServiceMessage() {
		String response=propertiesConfigServer.getServiceMessage();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
