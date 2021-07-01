package com.customer.validation;

import java.io.IOException;

import com.customer.pojo.AddressPojo;
import com.customer.services.JsonService;
import com.customer.services.JsonServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.google.inject.Inject;

import spark.Request;

public class AddressValidationImpl implements AddressValidation{
	
	@Inject
	JsonService jsonService;
	
	//simples valida��o de dados
	@Override
	public AddressPojo validate(Request request) throws IOException {
		AddressPojo addressPojo = null;
		
		try {
			addressPojo = jsonService.fromJson(request.body(), AddressPojo.class);
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
		
		if(!ExistAddressOnCustomer(requestBody)) {
			if(ExistAddressOnRequestBody(requestBody)) {
				node = JsonServiceImpl.StringToJson(requestBody);
			}else {
				apiError.setCode("json_format");
				apiError.setDescription("Falha, Json do corpo da requisi��o Inv�lido.");
				
				return apiError;
			}
		}else {
			node = JsonServiceImpl.StringToJson(requestBody).findValues("address").get(0);
		}
		
		Pattern regexPattern = null;
        Matcher regMatcher = null;
        
		if(node.get("state") == null) {
			apiError.setCode("json_filter");
			apiError.setDescription("Estado(state) � um campo obrigat�rio.");
			
			return apiError;
		}else{
			regexPattern = Pattern.compile("AC|AL|AP|AM|BA|CE|DF|ES|GO|MA|MT|MS|MG|PA|PB|PR|PE|PI|RJ|RN|RS|RO|RR|SC|SP|SE|TO");
	        regMatcher = regexPattern.matcher(node.get("state").asText());
			if(!regMatcher.matches()) {
				apiError.setCode("json_filter");
				apiError.setDescription("Estado(state) inv�lido, considere o formato ex: 'SP'.");
				
				return apiError;
			}
		}
		if(node.get("city") == null) {
			apiError.setCode("json_filter");
			apiError.setDescription("Cidade(city) � um campo obrigat�rio.");
			
			return apiError;
		}
		if(node.get("neighborhood") == null) {
			apiError.setCode("json_filter");
			apiError.setDescription("Bairro(neighborhood) � um campo obrigat�rio.");
			
			return apiError;
		}
		if(node.get("zipCode") == null) {
			apiError.setCode("json_filter");
			apiError.setDescription("Cep(zipCode) � um campo obrigat�rio.");
			
			return apiError;
		}else {
			regexPattern = Pattern.compile("[0-9]{5}[-][0-9]{3}");
	        regMatcher = regexPattern.matcher(node.get("zipCode").asText());
	       
	        if(!regMatcher.matches()) {
				apiError.setCode("json_filter");
				apiError.setDescription("Cep(zipCode) inv�lido, considere o formato ex: '06432-444'.");
				
				return apiError;
	        }
		}
		if(node.get("street") == null) {
			apiError.setCode("json_filter");
			apiError.setDescription("Rua(street) � um campo obrigat�rio.");
			
			return apiError;
		}
		if(node.get("number") == null) {
			}else {
				regexPattern = Pattern.compile("\\d+");
				regMatcher = regexPattern.matcher(node.get("number").asText());
				
				if(!regMatcher.matches()) {
					apiError.setCode("json_filter");
					apiError.setDescription("Numero(number) deve ser somente n�meros.");
					
					return apiError;
				}
		}
		if(node.get("main") == null) {
			apiError.setCode("json_filter");
			apiError.setDescription("Endere�o principal(main) � um campo obrigat�rio.");
			
			return apiError;
		}else if(node.get("main").asBoolean() == true || node.get("main").asBoolean() == false){
		}else {
			apiError.setCode("json_filter");
			apiError.setDescription("Endere�o principal(main) inv�lido, considere 'true' ou 'false'.");
			
			return apiError;
		}*/
		return addressPojo;
				
	}
	
	public boolean ExistAddressOnRequestBody(String requestBody) throws IOException{
		JsonNode node = JsonServiceImpl.StringToJson(requestBody);
		if(node.findValue("state") != null) {
			return true;
		}else if (node.findValue("city") != null) {
			return true;
		}else if (node.findValue("neighborhood") != null) {
			return true;
		}else if (node.findValue("zipCode") != null) {
			return true;
		}else if (node.findValue("street") != null) {
			return true;
		}else if (node.findValue("number") != null) {
			return true;
		}else if (node.findValue("additionalInformation") != null) {
			return true;
		}else if (node.findValue("main") != null) {
			return true;
		}else {
			return false;
		}
	}

	public boolean ExistAddressOnCustomer(String requestBody) throws IOException{
		try {
			if(JsonServiceImpl.StringToJson(requestBody).findValues("address").get(0).size() > 0) {
				return true;
			}else {
				return false;
			}
		} catch (Exception e) {
			if(e instanceof IndexOutOfBoundsException)
				return false;
		}
		return false;
	}
}
