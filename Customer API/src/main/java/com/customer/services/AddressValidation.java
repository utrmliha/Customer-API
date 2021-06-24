package com.customer.services;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.customer.dto.ApiError;
import com.customer.interfaces.Validation;
import com.fasterxml.jackson.databind.JsonNode;

public class AddressValidation implements Validation{

	ApiError apiError;
	
	//simples validação de dados
	@Override
	public ApiError validate(String requestBody) throws IOException {
		apiError = new ApiError();
		JsonNode node;
		
		if(!ExistAddressOnCustomer(requestBody)) {
			if(ExistAddressOnRequestBody(requestBody)) {
				node = JsonParsing.StringToJson(requestBody);
			}else {
				apiError.setCode("json_format");
				apiError.setDescription("Falha, Json do corpo da requisição Inválido.");
				
				return apiError;
			}
		}else {
			node = JsonParsing.StringToJson(requestBody).findValues("address").get(0);
		}
		
		Pattern regexPattern = null;
        Matcher regMatcher = null;
        
		if(node.get("state") == null) {
			apiError.setCode("json_filter");
			apiError.setDescription("Estado(state) é um campo obrigatório.");
			
			return apiError;
		}else{
			regexPattern = Pattern.compile("AC|AL|AP|AM|BA|CE|DF|ES|GO|MA|MT|MS|MG|PA|PB|PR|PE|PI|RJ|RN|RS|RO|RR|SC|SP|SE|TO");
	        regMatcher = regexPattern.matcher(node.get("state").asText());
			if(!regMatcher.matches()) {
				apiError.setCode("json_filter");
				apiError.setDescription("Estado(state) inválido, considere o formato ex: 'SP'.");
				
				return apiError;
			}
		}
		if(node.get("city") == null) {
			apiError.setCode("json_filter");
			apiError.setDescription("Cidade(city) é um campo obrigatório.");
			
			return apiError;
		}
		if(node.get("neighborhood") == null) {
			apiError.setCode("json_filter");
			apiError.setDescription("Bairro(neighborhood) é um campo obrigatório.");
			
			return apiError;
		}
		if(node.get("zipCode") == null) {
			apiError.setCode("json_filter");
			apiError.setDescription("Cep(zipCode) é um campo obrigatório.");
			
			return apiError;
		}else {
			regexPattern = Pattern.compile("[0-9]{5}[-][0-9]{3}");
	        regMatcher = regexPattern.matcher(node.get("zipCode").asText());
	       
	        if(!regMatcher.matches()) {
				apiError.setCode("json_filter");
				apiError.setDescription("Cep(zipCode) inválido, considere o formato ex: '06432-444'.");
				
				return apiError;
	        }
		}
		if(node.get("street") == null) {
			apiError.setCode("json_filter");
			apiError.setDescription("Rua(street) é um campo obrigatório.");
			
			return apiError;
		}
		if(node.get("number") == null) {
			}else {
				regexPattern = Pattern.compile("\\d+");
				regMatcher = regexPattern.matcher(node.get("number").asText());
				
				if(!regMatcher.matches()) {
					apiError.setCode("json_filter");
					apiError.setDescription("Numero(number) deve ser somente números.");
					
					return apiError;
				}
		}
		if(node.get("main") == null) {
			apiError.setCode("json_filter");
			apiError.setDescription("Endereço principal(main) é um campo obrigatório.");
			
			return apiError;
		}else if(node.get("main").asBoolean() == true || node.get("main").asBoolean() == false){
		}else {
			apiError.setCode("json_filter");
			apiError.setDescription("Endereço principal(main) inválido, considere 'true' ou 'false'.");
			
			return apiError;
		}
		return null;
				
	}
	
	public boolean ExistAddressOnRequestBody(String requestBody) throws IOException{
		JsonNode node = JsonParsing.StringToJson(requestBody);
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
			if(JsonParsing.StringToJson(requestBody).findValues("address").get(0).size() > 0) {
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
