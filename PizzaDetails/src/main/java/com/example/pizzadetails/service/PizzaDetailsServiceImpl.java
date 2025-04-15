package com.example.pizzadetails.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pizzadetails.controller.PizzaDetailsController;
import com.example.pizzadetails.dto.PizzaDetailsDTO;
import com.example.pizzadetails.entity.PizzaDetailsEntity;
import com.example.pizzadetails.repository.PizzaDetailsRepository;

@Service
public class PizzaDetailsServiceImpl implements PizzaDetailsService {
	
	@Autowired
	
	private PizzaDetailsRepository pizzaDetailsRepository;
	private static final Logger log = LoggerFactory.getLogger(PizzaDetailsController.class);

	/*@Override
	public boolean verifyPizzaAvailability(Long pizzaId, int quantity) {
		Optional<PizzaDetailsEntity> entity=pizzaDetailsRepository.findByPizzaId(pizzaId);
		if(entity.isPresent()) {
			log.info("Pizza Available entity is present");
			PizzaDetailsEntity pizzaEntity=entity.get();
			return pizzaEntity.getQuantity()>=quantity;
		}
		return false;
	}*/
	@Override
	public PizzaDetailsDTO getPizzaDetails(Long pizzaId) {
		Optional<PizzaDetailsEntity> entity=pizzaDetailsRepository.findByPizzaId(pizzaId);
		if(entity.isPresent()) {
			log.info("Pizza Available entity is present");
			PizzaDetailsEntity pizzaEntity=entity.get();
			return mapToDTO(pizzaEntity);
		}
		return null;
	}
	public PizzaDetailsDTO mapToDTO(PizzaDetailsEntity pizzaDetailsEntity) {
		return new PizzaDetailsDTO(pizzaDetailsEntity.getPizzaId(),pizzaDetailsEntity.getQuantity(),pizzaDetailsEntity.getPizzaName(),
				pizzaDetailsEntity.getPrice());
	}
	@Override
	public String addNewPizza(PizzaDetailsDTO pizzaDetailsDTO) {
		if(null!=pizzaDetailsDTO) {
			PizzaDetailsEntity pizzaEntity=new PizzaDetailsEntity(pizzaDetailsDTO.getPizzaId(),pizzaDetailsDTO.getPizzaName(),
					pizzaDetailsDTO.getQuantityChange(),pizzaDetailsDTO.getPrice());
			pizzaDetailsRepository.save(pizzaEntity);
			return "New pizza added successfully!!!";
		}
		else {
			throw new RuntimeException();
		}
	}

	@Override
	public String updatePizza(PizzaDetailsDTO pizzaDetailsDTO) {
		PizzaDetailsEntity pizzaDetails=pizzaDetailsRepository.findByPizzaId(pizzaDetailsDTO.getPizzaId())
				.orElseThrow(() -> new RuntimeException("PizzaId not found in PizzaDetails"));
		int quantityUpdate=pizzaDetails.getQuantity()+pizzaDetailsDTO.getQuantityChange();
		if(quantityUpdate < 0) {
			throw new RuntimeException("No pizza available for PizzaId: "+ pizzaDetailsDTO.getPizzaId());
		}
		pizzaDetails.setQuantity(quantityUpdate);
		pizzaDetails.setPrice(pizzaDetailsDTO.getPrice()!=0.0?pizzaDetailsDTO.getPrice():pizzaDetails.getPrice());
		pizzaDetailsRepository.save(pizzaDetails);
		return "Pizza quantity updated successfully!!!";
	}
}
