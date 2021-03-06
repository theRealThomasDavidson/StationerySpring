package com.cognixia.jump.model;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Inventory {
	@Id				//primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)	//auto increment
	private Long id;
	
	@Column(unique=true, nullable = false)
	private String name;
	
	@Column(columnDefinition = "varchar(255) default 'This can helpp you with your stationery needs'")
	private String description;
	
	@Column(columnDefinition = "int default 0", nullable = false)
	private Integer inventory;
	
	@JsonIgnore
	@OneToMany(mappedBy= "id", cascade = CascadeType.ALL)
	private List<Item> items;
	
	public Inventory() {
		this.items = new ArrayList<Item>();
	}

	public Inventory(String name, String description, Integer inventory) {
		super();
		this.name = name;
		this.description = description;
		this.inventory = inventory;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getInventory() {
		return inventory;
	}

	public void setInventory(Integer inventory) {
		this.inventory = inventory;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", description=" + description + ", inventory=" + inventory
				+ ", getClass()=" + getClass() + "]";
	}
	
}
