package com.customer.dto;

import java.beans.ConstructorProperties;

public class Address {
	private Integer id;
	private String state;
	private String city;
	private String neighborhood;
	private String zipCode;
	private String street;
	private String number;
	private String additionalInformation;
	private Boolean main;
	
	@ConstructorProperties({"id", "state", "city", "neighborhood", "zipCode", "street", "number", "additionalInformation", "main"})
	public Address(Integer id, String state, String city, String neighborhood, String zipCode, String street,
			String number, String additionalInformation, Boolean main) {
		super();
		this.id = id;
		this.state = state;
		this.city = city;
		this.neighborhood = neighborhood;
		this.zipCode = zipCode;
		this.street = street;
		this.number = number;
		this.additionalInformation = additionalInformation;
		this.main = main;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
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
	public String getNeighborhood() {
		return neighborhood;
	}
	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getAdditionalInformation() {
		return additionalInformation;
	}
	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}
	public Boolean getMain() {
		return main;
	}
	public void setMain(Boolean main) {
		this.main = main;
	}
	
	
}
