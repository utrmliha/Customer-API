package com.customer.validation;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.customer.filter.AddressFilter;
import com.customer.filter.CustomerFilter;
import com.customer.services.JsonService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.google.inject.Inject;

import spark.Request;

public class AddressFilterValidationImpl implements AddressFilterValidation{

	@Inject
	JsonService jsonService;
	
	@Override
	public AddressFilter validate(Request request){
		AddressFilter addressFilter = null;
		try {
			addressFilter = jsonService.fromJson(request.body(), AddressFilter.class);
		}catch (Exception e) {
			if(e instanceof MismatchedInputException) {
				return null;
			}else {
				e.printStackTrace();//ERRO JSON INVÁLIDO
			}
		}
		/*
		ApiError apiError = null;
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
        
        if(node.get("id") == null) {
		}else {
			regexPattern = Pattern.compile("\\d+");
			regMatcher = regexPattern.matcher(node.get("id").asText());
			
			if(!regMatcher.matches()) {
				apiError.setCode("json_filter");
				apiError.setDescription("Id deve ser somente números.");
				
				return apiError;
			}
		}
        if(node.get("state") == null) {
		}else {
			regexPattern = Pattern.compile("AC|AL|AP|AM|BA|CE|DF|ES|GO|MA|MT|MS|MG|PA|PB|PR|PE|PI|RJ|RN|RS|RO|RR|SC|SP|SE|TO");
			regMatcher = regexPattern.matcher(node.get("state").asText());
			
			if(!regMatcher.matches()) {
				apiError.setCode("json_filter");
				apiError.setDescription("Estado(state) inválido, considere o formato ex: 'SP'.");
				
				return apiError;
			}
		}
        if(node.get("zipCode") == null) {
		}else {
			regexPattern = Pattern.compile("[0-9]{5}[-][0-9]{3}");
	        regMatcher = regexPattern.matcher(node.get("zipCode").asText());
	       
	        if(!regMatcher.matches()) {
				apiError.setCode("json_filter");
				apiError.setDescription("Cep(zipCode) inválido, considere o formato ex: '06432-444'.");
				
				return apiError;
	        }
		}
        if(node.get("number") == null) {
		}else {
			regexPattern = Pattern.compile("\\d+");
			regMatcher = regexPattern.matcher(node.get("id").asText());
			
			if(!regMatcher.matches()) {
				apiError.setCode("json_filter");
				apiError.setDescription("Numero(number) deve ser somente números.");
				
				return apiError;
			}
		}
        if(node.get("main") == null) {
		}else if(node.get("main").asBoolean() == true || node.get("main").asBoolean() == false){
		}else {
			apiError.setCode("json_filter");
			apiError.setDescription("Endereço principal(main) inválido, considere 'true' ou 'false'.");
			
			return apiError;
		}
        if(node.findValue("sortBy") != null) {
        	regexPattern = Pattern.compile("ID|STATE|CITY|NEIGHBORHOOD|ZIPCODE|STREET|NUMBER|ADDITIONALINFORMATION|MAIN");
        	regMatcher = regexPattern.matcher(node.get("sortBy").asText());
        	if(!regMatcher.matches()) {
        		apiError.setCode("json_filter");
        		apiError.setDescription("Ordenar por(sortBy) inválido, considere um destes valores: 'ID, STATE, CITY, NEIGHBORHOOD, ZIPCODE, STREET, NUMBER, ADDITIONALINFORMATION ou MAIN'.");
        			
        		return apiError;
        	}
        }
        if(node.findValue("sortOrder") != null) {
        	regexPattern = Pattern.compile("ASC|DESC");
        	regMatcher = regexPattern.matcher(node.get("sortOrder").asText());
        	if(!regMatcher.matches()) {
        		apiError.setCode("json_filter");
        		apiError.setDescription("ordem de classificação(sortOrder) inválido, considere um destes valores: 'ASC ou DESC'.");
        		
        		return apiError;
        	}
        }*/
		return addressFilter;
	}

}
