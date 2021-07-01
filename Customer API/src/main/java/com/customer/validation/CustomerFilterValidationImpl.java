package com.customer.validation;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.customer.filter.CustomerFilter;
import com.customer.services.ApiError;
import com.customer.services.JsonService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.google.inject.Inject;

import spark.Request;

public class CustomerFilterValidationImpl implements CustomerFilterValidation {

	ApiError apiError;
	@Inject
	JsonService jsonService;
	
	@Override
	public CustomerFilter validate(Request request){
		CustomerFilter customerFilter = null;
		try {
			customerFilter = jsonService.fromJson(request.body(), CustomerFilter.class);
		}catch (Exception e) {
			if(e instanceof MismatchedInputException) {
				return null;
			}else {
				e.printStackTrace();
			}
		}
		
		/*
		apiError = null;
		JsonNode node;
		
		try {
			node = JsonServiceImpl.StringToJson(requestBody);
		}catch (Exception e) {
			apiError.setCode("json_format");
			apiError.setDescription("Falha, Json do corpo da requisição Inválido.");
			
			return apiError;
		}
		
		Pattern regexPattern = null;
        Matcher regMatcher = null;
        
        if(node.findValue("birthDate") != null) {
        	regexPattern = Pattern.compile("^((?:(?:1[6-9]|2[0-9])\\d{2})(-)(?:(?:(?:0[13578]|1[02])(-)31)|((0[1,3-9]|1[0-2])(-)(29|30))))$|^(?:(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00)))(-)02(-)29)$|^(?:(?:1[6-9]|2[0-9])\\d{2})(-)(?:(?:0[1-9])|(?:1[0-2]))(-)(?:0[1-9]|1\\d|2[0-8])$");
        	regMatcher = regexPattern.matcher(node.get("birthDate").asText());
        	
        	if(!regMatcher.matches()) {
        		apiError.setCode("json_filter");
        		apiError.setDescription("Data de nascimento(birthDate) inválida, considere o formato ex: '1980-12-28'.");
        		
        		return apiError;
        	}
        }
        if(node.findValue("sortBy") != null) {
        	if(node.get("sortBy") == null) {
        	}else {
        		regexPattern = Pattern.compile("CUSTOMER_NAME|CUSTOMER_BIRTH_DATE|CUSTOMER_CREATED_AT|ADDRESS_STATE|ADDRESS_CITY");
        		regMatcher = regexPattern.matcher(node.get("sortBy").asText());
        		if(!regMatcher.matches()) {
        			apiError.setCode("json_filter");
        			apiError.setDescription("Ordenar por(sortBy) inválido, considere um destes valores: 'CUSTOMER_NAME, CUSTOMER_BIRTH_DATE, CUSTOMER_CREATED_AT, ADDRESS_STATE ou ADDRESS_CITY'.");
        			
        			return apiError;
        		}
        	}
        }
        if(node.findValue("sortOrder") != null) {
        	if(node.get("sortOrder") == null) {
        	}else {
        		regexPattern = Pattern.compile("ASC|DESC");
        		regMatcher = regexPattern.matcher(node.get("sortOrder").asText());
        		if(!regMatcher.matches()) {
        			apiError.setCode("json_filter");
        			apiError.setDescription("ordem de classificação(sortOrder) inválido, considere um destes valores: 'ASC ou DESC'.");
        			
        			return apiError;
        		}
        	}
        }
		*/
		return customerFilter;
	}
	
}
