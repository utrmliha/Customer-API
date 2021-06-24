package com.customer.pojo;

public class CustomerPojo {
	private Integer id;
	private String name;
	private String email;
	private String birthDate;
	private String cpf;
	private String gender;
	private AddressPojo address;
		
	public void setGender(String gender) {
		this.gender = gender.toUpperCase();
	}

	public Integer getId() {
		return id;
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

	public String getGender() {
		return gender;
	}

	public AddressPojo getAddress() {
		return address;
	}
	
	public void setAddress(AddressPojo address) {
		this.address = address;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
}
