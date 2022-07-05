package com.cognixia.jump.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Address {

	@Id				//primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)	//auto increment
	private Long id;

	@Pattern(regexp = "^[A-Z]{2}$")
	@Size(min=2, max=2, message = "Please use the 2 letter abbreviation")
	@Column(columnDefinition = "char(2) default 'XX'")
	private String state;

	@NotBlank(message = "street address can't be left blank")
	@Size(min=1, max=80, message = "city must be between 1 and 80 characters")
	@Column(columnDefinition = "VARCHAR(80) default 'n/a'")
	private String city;
	
	@NotBlank(message = "street address can't be left blank")
	private String street1;
	
	private String street2;

	@Pattern(regexp = "^[0-9]{5}$")
	@Column(columnDefinition = "char(5) default '00000'")
	private String zip;
	
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy="address")
	@JsonIgnore
	private User user;
	
	public Address() {}
	
	@Override
	public String toString() {
		return "Address [id=" + id + ", state=" + state + ", city=" + city + ", street1=" + street1 + ", street2="
				+ street2 + ", zip=" + zip + ", getClass()=" + getClass() + "]";
	}

	public Address(
			@Pattern(regexp = "^[A-Z]{2}$") @Size(min = 2, max = 2, message = "Please use the 2 letter abbreviation") String state,
			@NotBlank(message = "street address can't be left blank") @Size(min = 1, max = 80, message = "city must be between 1 and 80 characters") String city,
			@NotBlank(message = "street address can't be left blank") String street1, String street2,
			@Pattern(regexp = "^[0-9]{5}$") String zip) {
		super();
		this.state = state;
		this.city = city;
		this.street1 = street1;
		this.street2 = street2;
		this.zip = zip;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStreet1() {
		return street1;
	}
	public void setStreet1(String street1) {
		this.street1 = street1;
	}
	public String getStreet2() {
		return street2;
	}
	public void setStreet2(String street2) {
		this.street2 = street2;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	
	
}
