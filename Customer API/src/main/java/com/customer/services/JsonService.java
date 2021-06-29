package com.customer.services;

public interface JsonService {

	public <T> T fromJson(String json, Class<T> classType);
	
	public String toJson(Object T);
}
