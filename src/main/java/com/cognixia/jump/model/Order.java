package com.cognixia.jump.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;



@Entity
@Table(name="Shopping") 
public class Order {
	public static enum Status {
		CART, TO_SEND, IN_MAIL, RECIEVED   // roles should start with capital ROLE_ and be completely capital letters
	}
	
	@Id				//primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)	//auto increment
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "user", referencedColumnName = "id")
	private User user;
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy= "id", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Item> items;
	
	public Order() {
		this.items = new ArrayList<Item>();
	}

	public Order(Long id, User user, Status status, List<Item> items) {
		super();
		this.id = id;
		this.user = user;
		this.status = status;
		this.items = items;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
	public void setNewItems() {
		for(Item i: this.items) {
			i.setId(null);
			i.setOrder(this);
		}
	}
	public void updateItems(List<Item> items) {
		for(Item i: this.items) {
			i.setOrder(this);
		}
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", user=" + user + ", status=" + status + ", items=" + items + ", getClass()="
				+ getClass() + "]";
	}
	
}
