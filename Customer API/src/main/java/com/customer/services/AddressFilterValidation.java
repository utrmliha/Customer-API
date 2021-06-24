package com.customer.services;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.customer.dto.ApiError;
import com.customer.interfaces.IValidation;
import com.fasterxml.jackson.databind.JsonNode;

public class AddressFilterValidation implements IValidation{

	@Override
	public ApiError validate(String requestBody) throws IOException {
		ApiError apiError = new ApiError();
		JsonNode node;
		
		try {
			node = JsonParsing.StringToJson(requestBody);
		}catch (Exception e) {
			apiError.setCode("json_format");
			apiError.setDescription("Falha, Json do corpo da requisi��o Inv�lido.");
			
			return apiError;
		}
		
		Pattern regexPattern = null;
        Matcher regMatcher = null;
        
        if(node.get("id") == null || node.get("id").asText().isBlank()) {
		}else {
			regexPattern = Pattern.compile("\\d+");
			regMatcher = regexPattern.matcher(node.get("id").asText());
			
			if(!regMatcher.matches()) {
				apiError.setCode("json_filter");
				apiError.setDescription("Id deve ser somente n�meros.");
				
				return apiError;
			}
		}
        if(node.get("state") == null || node.get("state").asText().isBlank()) {
		}else {
			regexPattern = Pattern.compile("AC|AL|AP|AM|BA|CE|DF|ES|GO|MA|MT|MS|MG|PA|PB|PR|PE|PI|RJ|RN|RS|RO|RR|SC|SP|SE|TO");
			regMatcher = regexPattern.matcher(node.get("state").asText());
			
			if(!regMatcher.matches()) {
				apiError.setCode("json_filter");
				apiError.setDescription("Estado(state) inv�lido, considere o formato ex: 'SP'.");
				
				return apiError;
			}
		}
        if(node.get("zipCode") == null || node.get("zipCode").asText().isBlank()) {
		}else {
			regexPattern = Pattern.compile("[0-9]{5}[-][0-9]{3}");
	        regMatcher = regexPattern.matcher(node.get("zipCode").asText());
	       
	        if(!regMatcher.matches()) {
				apiError.setCode("json_filter");
				apiError.setDescription("Cep(zipCode) inv�lido, considere o formato ex: '06432-444'.");
				
				return apiError;
	        }
		}
        if(node.get("number") == null || node.get("number").asText().isBlank()) {
		}else {
			regexPattern = Pattern.compile("\\d+");
			regMatcher = regexPattern.matcher(node.get("id").asText());
			
			if(!regMatcher.matches()) {
				apiError.setCode("json_filter");
				apiError.setDescription("Numero(number) deve ser somente n�meros.");
				
				return apiError;
			}
		}
        if(node.get("main") == null || node.get("main").asText().isBlank()) {
		}else if(node.get("main").asBoolean() == true || node.get("main").asBoolean() == false){
		}else {
			apiError.setCode("json_filter");
			apiError.setDescription("Endere�o principal(main) inv�lido, considere 'true' ou 'false'.");
			
			return apiError;
		}
        if(node.findValue("sortBy") != null) {
        	regexPattern = Pattern.compile("ID|STATE|CITY|NEIGHBORHOOD|ZIPCODE|STREET|NUMBER|ADDITIONALINFORMATION|MAIN");
        	regMatcher = regexPattern.matcher(node.get("sortBy").asText());
        	if(!regMatcher.matches()) {
        		apiError.setCode("json_filter");
        		apiError.setDescription("Ordenar por(sortBy) inv�lido, considere um destes valores: 'ID, STATE, CITY, NEIGHBORHOOD, ZIPCODE, STREET, NUMBER, ADDITIONALINFORMATION ou MAIN'.");
        			
        		return apiError;
        	}
        }
        if(node.findValue("sortOrder") != null) {
        	regexPattern = Pattern.compile("ASC|DESC");
        	regMatcher = regexPattern.matcher(node.get("sortOrder").asText());
        	if(!regMatcher.matches()) {
        		apiError.setCode("json_filter");
        		apiError.setDescription("ordem de classifica��o(sortOrder) inv�lido, considere um destes valores: 'ASC ou DESC'.");
        		
        		return apiError;
        	}
        }
		return null;
	}

}