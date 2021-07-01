package com.customer.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface JsonService {

	public <T> T fromJson(String json, Class<T> classType) throws JsonMappingException, JsonProcessingException ;
	
	public String toJson(Object T) throws JsonProcessingException;
}
