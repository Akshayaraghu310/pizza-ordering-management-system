package com.example.pizzadetails.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pizzadetails.entity.PizzaDetailsEntity;

@Repository
public interface PizzaDetailsRepository extends JpaRepository<PizzaDetailsEntity, Long> {

	Optional<PizzaDetailsEntity> findByPizzaId(Long pizzaId);

}
