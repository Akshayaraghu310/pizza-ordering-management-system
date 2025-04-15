package com.example.orderdetails.dto;

public class PizzaDetailsDTO {
	private Long pizzaId;
	private int quantityChange;
	private String pizzaName;
	private double price;
	
	public PizzaDetailsDTO() {
		
	}
	public PizzaDetailsDTO(Long pizzaId, int quantityChange, String pizzaName, double price) {
		super();
		this.pizzaId = pizzaId;
		this.quantityChange = quantityChange;
		this.pizzaName=pizzaName;
		this.price=price;
	}
	
	public Long getPizzaId() {
		return pizzaId;
	}
	public void setPizzaId(Long pizzaId) {
		this.pizzaId = pizzaId;
	}
	public int getQuantityChange() {
		return quantityChange;
	}
	public void setQuantityChange(int quantityChange) {
		this.quantityChange = quantityChange;
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
		return "PizzaDetailsDTO [pizzaId=" + pizzaId + ", quantityChange=" + quantityChange + ", pizzaName=" + pizzaName
				+ ", price=" + price + "]";
	}
}
