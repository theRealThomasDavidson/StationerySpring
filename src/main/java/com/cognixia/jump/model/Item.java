package com.cognixia.jump.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
public class Item {
	@Id				//primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)	//auto increment
	private Long id;
	
	@Column(columnDefinition = "int default 1", nullable = false)
	private Integer qty;


	
	@ManyToOne
	@JoinColumn(name = "order_id", referencedColumnName = "id")
	@JsonBackReference
	private Order order;
	

	@ManyToOne
	@JoinColumn(name = "inventory_id", referencedColumnName = "id")
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
	
	@JsonBackReference
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
