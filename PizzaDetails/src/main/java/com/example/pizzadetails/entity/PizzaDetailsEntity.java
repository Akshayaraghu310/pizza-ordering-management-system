package com.example.pizzadetails.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="pizzadetails")
public class PizzaDetailsEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private Long pizzaId;
	private String pizzaName;
	private int quantity;
	private double price;
	
	public PizzaDetailsEntity() {
		
	}
	public PizzaDetailsEntity(Long pizzaId, String pizzaName,int quantity, double price) {
		super();
		this.pizzaId = pizzaId;
		this.pizzaName=pizzaName;
		this.quantity = quantity;
		this.price=price;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPizzaId() {
		return pizzaId;
	}
	public void setPizzaId(Long pizzaId) {
		this.pizzaId = pizzaId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getPizzaName() {
		return pizzaName;
	}
	public void setPizzaName(String pizzaName) {
		this.pizzaName = pizzaName;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "PizzaDetailsEntity [id=" + id + ", pizzaId=" + pizzaId + ", pizzaName=" + pizzaName + ", quantity="
				+ quantity + ", price=" + price + "]";
	}
}
