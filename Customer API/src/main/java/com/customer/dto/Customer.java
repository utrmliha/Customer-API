package com.customer.dto;

import java.util.ArrayList;
import java.util.List;

public class Customer {
	private Long id;
	private String uuid;
	private String name;
	private String email;
	private String birthDate;
	private String cpf;
	private String gender;
	private Address mainAddress;
	private List<Address> adresses;
	private String createdAt;
	private String updateAt; 
	
	public void setGender(String gender) {
		this.gender = gender.toUpperCase();
	}

	public Long getId() {
		return id;
	}

	public String getUuid() {
		return uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	
	public String getUpdateAt() {
		return updateAt;
	}
	
	public void setUpdateAt(String updateAt) {
		this.updateAt = updateAt;
	}

	public Address getMainAddress() {
		return mainAddress;
	}

	public void setMainAddress(Address mainAddress) {
		this.mainAddress = mainAddress;
	}

	public List<Address> getAdresses() {
		if(adresses == null) {
			adresses = new ArrayList<Address>();
			return adresses;
		}
		else 
			return adresses;
	}

	public void setAdresses(List<Address> adresses) {
		this.adresses = adresses;
	}

	public String getGender() {
		return gender;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	
	
	
	
}
