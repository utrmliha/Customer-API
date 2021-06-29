package com.customer.pojo;

public class CustomerPojo {
	private Long id;
	private String name;
	private String email;
	private String birthDate;
	private String cpf;
	private String gender;
	private AddressPojo address;
	private String createdAt;
	private String updateAt;
	
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
	public void setGender(String gender) {
		this.gender = gender.toUpperCase();
	}

	public Long getId() {
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
	
	public void setId(Long id) {
		this.id = id;
	}
}
