package com.customer.dto;

import java.beans.ConstructorProperties;

public class AddressCustomer_InnerJoin {
	private Integer c_id;
	private Integer a_id;
	
	@ConstructorProperties({"c_id", "a_id"})
	public AddressCustomer_InnerJoin(Integer c_id, Integer a_id) {
		super();
		this.c_id = c_id;
		this.a_id = a_id;
	}
	
	public Integer getC_id() {
		return c_id;
	}
	public void setC_id(Integer c_id) {
		this.c_id = c_id;
	}
	public Integer getA_id() {
		return a_id;
	}
	public void setA_id(Integer a_id) {
		this.a_id = a_id;
	}
	

}
