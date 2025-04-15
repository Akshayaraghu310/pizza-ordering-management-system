package com.example.customerdetails.dto;

public class ItemsOrderedDTO {
	private Long orderId;
	private Long pizzaId;
	private int quantity;
	private double price;

	public ItemsOrderedDTO() {

	}
	public ItemsOrderedDTO(Long orderId, Long pizzaId, int quantity, double price) {
		super();
		this.orderId = orderId;
		this.pizzaId = pizzaId;
		this.quantity = quantity;
		this.price = price;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "ItemsOrderedDTO [orderId=" + orderId + ", pizzaId=" + pizzaId + ", quantity=" + quantity + ", price="
				+ price + "]";
	}
}