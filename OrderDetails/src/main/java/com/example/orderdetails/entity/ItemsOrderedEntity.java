package com.example.orderdetails.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="itemsordered")
public class ItemsOrderedEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long pizzaId;
	private int quantity;
	
	@ManyToOne
	@JoinColumn(name="order_details_id")
	private OrderDetailsEntity orderDetails;

	public ItemsOrderedEntity() {
		
		}

	public ItemsOrderedEntity(Long id, Long pizzaId, int quantity) {
		this.id = id;
		this.pizzaId = pizzaId;
		this.quantity = quantity;
	}


	public Long geId() {
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

	public OrderDetailsEntity getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(OrderDetailsEntity orderDetails) {
		this.orderDetails = orderDetails;
	}

	@Override
	public String toString() {
		return "ItemsOrderedEntity [id=" + id + ", pizzaId=" + pizzaId + ", quantity=" + quantity + ", orderDetails="
				+ orderDetails + "]";
	}
}
