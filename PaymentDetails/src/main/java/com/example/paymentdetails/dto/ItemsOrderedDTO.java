package com.example.paymentdetails.dto;

public class ItemsOrderedDTO {
	private Long orderId;
	private Long pizzaId;
	private int quantity;

	public ItemsOrderedDTO() {

	}
	public ItemsOrderedDTO(Long orderId, Long pizzaId, int quantity) {
		super();
		this.orderId = orderId;
		this.pizzaId = pizzaId;
		this.quantity = quantity;
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
	@Override
	public String toString() {
		return "ItemsOrderedDTO [orderId=" + orderId + ", pizzaId=" + pizzaId + ", quantity=" + quantity + "]";
	}
}