package com.cognixia.jump.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
public class User {
	
	public static enum Role {
		ROLE_USER, ROLE_ADMIN   // roles should start with capital ROLE_ and be completely capital letters
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotBlank
	@Column(nullable = false)
	private String username;

	@NotBlank
	@Column(nullable = false)
	private String email;
	
	@NotBlank
	@Column(nullable = false)
	private String password;

	@JsonIgnore
	@OneToOne(cascade= CascadeType.ALL)
	@JoinColumn(name= "address_id")
	private Address address;
	
	@Column(columnDefinition = "boolean default true")
	private boolean enabled; // is user enabled? Are they currently able to use this account
	
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "varchar(255) not null default 'ROLE_USER'")
	private Role role;

	
	@OneToMany(mappedBy= "id", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Order> orders;
	
	public User() {
		this.orders = new ArrayList<Order>();
	}

	public User(Integer id, String username, String email, @NotBlank String password, Address address, boolean enabled,
			List<Order> orders) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.address = address;
		this.enabled = enabled;
		this.orders = orders;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
		
		for (Order o:this.orders){
			o.setId(null);
			o.setUser(this);
		}
	}
	public void setNewOrders(List<Order> orders) {
		
		for (Order o:orders){
			o.setId(null);
			o.setUser(this);
		}
	}
	public void updateOrders(List<Order> orders) {
		for (Order o:orders){
			o.setUser(this);
		}
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", email=" + email + ", password=" + password
				 + ", enabled=" + enabled + ", getClass()=" + getClass()
				+ "]";
	}
	

	
}