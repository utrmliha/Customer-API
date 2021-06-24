package com.customer.services;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.customer.dto.ApiError;
import com.customer.interfaces.Validation;
import com.fasterxml.jackson.databind.*;

public class CustomerValidation implements Validation{
	
	ApiError apiError;
	
	//simples valida��o de dados
	@Override
	public ApiError validate(String requestBody) throws IOException {
		apiError = new ApiError();
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

		if(node.get("name") == null) {
			apiError.setCode("json_filter");
			apiError.setDescription("Nome(name) do cliente � um campo obrigat�rio.");
			
			return apiError;
		}else{
			regexPattern = Pattern.compile("([0-9])");
	        regMatcher = regexPattern.matcher(node.get("name").asText());
	        
	        if(regMatcher.matches()) {
	        	apiError.setCode("json_filter");
	        	apiError.setDescription("Nome(name) do cliente inv�lido.");
	        	
	        	return apiError;
	        }
		}
		if(node.get("email") == null) {
			apiError.setCode("json_filter");
			apiError.setDescription("Email(email) do cliente � um campo obrigat�rio.");
			
			return apiError;
		}else {
			regexPattern = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
	        regMatcher = regexPattern.matcher(node.get("email").asText());
	        
	        if(!regMatcher.matches()) {
				apiError.setCode("json_filter");
				apiError.setDescription("Email(email) inv�lido, considere o formato ex: 'email@email.com'.");
				
				return apiError;
	        }
		}
		if(node.get("birthDate") == null) {
			apiError.setCode("json_filter");
			apiError.setDescription("Data de nascimento(birthDate) do cliente � um campo obrigat�rio.");
			
			return apiError;
		}else {
			regexPattern = Pattern.compile("^((?:(?:1[6-9]|2[0-9])\\d{2})(-)(?:(?:(?:0[13578]|1[02])(-)31)|((0[1,3-9]|1[0-2])(-)(29|30))))$|^(?:(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00)))(-)02(-)29)$|^(?:(?:1[6-9]|2[0-9])\\d{2})(-)(?:(?:0[1-9])|(?:1[0-2]))(-)(?:0[1-9]|1\\d|2[0-8])$");
	        regMatcher = regexPattern.matcher(node.get("birthDate").asText());
	        
	        if(!regMatcher.matches()) {
				apiError.setCode("json_filter");
				apiError.setDescription("Data de nascimento(birthDate) inv�lida, considere o formato ex: '1980-12-28'.");
				
				return apiError;
	        }else {
	        	int dia = Integer.parseInt(node.get("birthDate").asText().split("-")[2]);
	        	int mes = Integer.parseInt(node.get("birthDate").asText().split("-")[1].split("-")[0]);
	        	int ano = Integer.parseInt(node.get("birthDate").asText().split("-")[0]);
	        	int idade = LocalDate.now().minus(Period.of(ano, mes, dia)).getYear();
	        	
	        	if(idade > 100) {
					apiError.setCode("json_filter");
					apiError.setDescription("Data de nascimento(birthDate) inv�lida.");
	        	}
	        }
		}
		if(node.get("cpf") == null) {
			apiError.setCode("json_filter");
			apiError.setDescription("Cpf(cpf) do cliente � um campo obrigat�rio.");
			
			return apiError;
		}else {
			regexPattern = Pattern.compile("[0-9]{3}[0-9]{3}[0-9]{3}[0-9]{2}");
	        regMatcher = regexPattern.matcher(node.get("cpf").asText());
	        
	        if(regMatcher.matches()) {
				apiError.setCode("json_filter");
				apiError.setDescription("CPF(cpf) inv�lido, considere o formato ex: '756.684.987-69'.");
				
				return apiError;
	        }else{
				regexPattern = Pattern.compile("^[0-9]{3}[\\.][0-9]{3}[\\.][0-9]{3}[-][0-9]{2}$");
				Matcher regMatcher2 = regexPattern.matcher(node.get("cpf").asText());
				
		        if(!regMatcher2.matches()) {
					apiError.setCode("json_filter");
					apiError.setDescription("CPF(cpf) inv�lido.");
					
					return apiError;
		        }
		    }
		}
		if(node.get("gender") == null) {
			apiError.setCode("json_filter");
			apiError.setDescription("G�nero(gender) do cliente � um campo obrigat�rio.");
			
			return apiError;
		}else {
			String gender = node.get("gender").asText();
			
			if(gender.equalsIgnoreCase("FEMININO") || gender.equalsIgnoreCase("MASCULINO")) {
			}else {
				apiError.setCode("json_filter");
				apiError.setDescription("Genero(gender) inv�lido, considere: 'Feminino' ou 'Masculino'.");
				
				return apiError;
			}
		}
		return null;
	}
}