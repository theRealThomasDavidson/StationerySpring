package com.cognixia.jump.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class Item {
	@Id				//primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)	//auto increment
	private Long id;
	
	@Column(columnDefinition = "int default 1", nullable = false)
	private Integer qty;
	
	@ManyToOne
	@JoinColumn(name = "order", referencedColumnName = "id")
	private Order order;
	
	@ManyToOne
	@JoinColumn(name = "inventory", referencedColumnName = "id")
	private Inventory inventory;
	
	public Item() {}

	public Item(Long id, Integer qty, Order order, Inventory inventory) {
		super();
		this.id = id;
		this.qty = qty;
		this.order = order;
		this.inventory = inventory;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", qty=" + qty + ", order=" + order + ", inventory=" + inventory + ", getClass()="
				+ getClass() + "]";
	}
	
}
