package com.customer.validation;

import com.customer.pojo.CustomerPojo;
import com.customer.services.JsonService;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.google.inject.Inject;

import spark.Request;

public class CustomerValidationImpl implements CustomerValidation{
	
	@Inject
	JsonService jsonService;
	
	//simples validação de dados
	@Override
	public CustomerPojo validate(Request request){
		CustomerPojo customerPojo = null;
		
		try {
			customerPojo = jsonService.fromJson(request.body(), CustomerPojo.class);
		}catch (Exception e) {
			if(e instanceof MismatchedInputException) {
				return null;
			}else {
				e.printStackTrace();
			}
		}
		
		/*
		Pattern regexPattern = null;
        Matcher regMatcher = null;

		if(customerPojo.getName() == null || customerPojo.getName().isBlank()) {
			System.out.println("Nome(name) do cliente é um campo obrigatório.");
		}else{
			regexPattern = Pattern.compile("([0-9])");
	        regMatcher = regexPattern.matcher(node.get("name").asText());
	        
	        if(regMatcher.matches()) {
	        	apiError.setCode("json_filter");
	        	apiError.setDescription("Nome(name) do cliente inválido.");
	        	
	        	return apiError;
	        }
		}
		if(node.get("email") == null) {
			apiError.setCode("json_filter");
			apiError.setDescription("Email(email) do cliente é um campo obrigatório.");
			
			return apiError;
		}else {
			regexPattern = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
	        regMatcher = regexPattern.matcher(node.get("email").asText());
	        
	        if(!regMatcher.matches()) {
				apiError.setCode("json_filter");
				apiError.setDescription("Email(email) inválido, considere o formato ex: 'email@email.com'.");
				
				return apiError;
	        }
		}
		if(node.get("birthDate") == null) {
			apiError.setCode("json_filter");
			apiError.setDescription("Data de nascimento(birthDate) do cliente é um campo obrigatório.");
			
			return apiError;
		}else {
			regexPattern = Pattern.compile("^((?:(?:1[6-9]|2[0-9])\\d{2})(-)(?:(?:(?:0[13578]|1[02])(-)31)|((0[1,3-9]|1[0-2])(-)(29|30))))$|^(?:(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00)))(-)02(-)29)$|^(?:(?:1[6-9]|2[0-9])\\d{2})(-)(?:(?:0[1-9])|(?:1[0-2]))(-)(?:0[1-9]|1\\d|2[0-8])$");
	        regMatcher = regexPattern.matcher(node.get("birthDate").asText());
	        
	        if(!regMatcher.matches()) {
				apiError.setCode("json_filter");
				apiError.setDescription("Data de nascimento(birthDate) inválida, considere o formato ex: '1980-12-28'.");
				
				return apiError;
	        }else {
	        	int dia = Integer.parseInt(node.get("birthDate").asText().split("-")[2]);
	        	int mes = Integer.parseInt(node.get("birthDate").asText().split("-")[1].split("-")[0]);
	        	int ano = Integer.parseInt(node.get("birthDate").asText().split("-")[0]);
	        	int idade = LocalDate.now().minus(Period.of(ano, mes, dia)).getYear();
	        	
	        	if(idade > 100) {
					apiError.setCode("json_filter");
					apiError.setDescription("Data de nascimento(birthDate) inválida.");
	        	}
	        }
		}
		if(node.get("cpf") == null) {
			apiError.setCode("json_filter");
			apiError.setDescription("Cpf(cpf) do cliente é um campo obrigatório.");
			
			return apiError;
		}else {
			regexPattern = Pattern.compile("[0-9]{3}[0-9]{3}[0-9]{3}[0-9]{2}");
	        regMatcher = regexPattern.matcher(node.get("cpf").asText());
	        
	        if(regMatcher.matches()) {
				apiError.setCode("json_filter");
				apiError.setDescription("CPF(cpf) inválido, considere o formato ex: '756.684.987-69'.");
				
				return apiError;
	        }else{
				regexPattern = Pattern.compile("^[0-9]{3}[\\.][0-9]{3}[\\.][0-9]{3}[-][0-9]{2}$");
				Matcher regMatcher2 = regexPattern.matcher(node.get("cpf").asText());
				
		        if(!regMatcher2.matches()) {
					apiError.setCode("json_filter");
					apiError.setDescription("CPF(cpf) inválido.");
					
					return apiError;
		        }
		    }
		}
		if(node.get("gender") == null) {
			apiError.setCode("json_filter");
			apiError.setDescription("Gênero(gender) do cliente é um campo obrigatório.");
			
			return apiError;
		}else {
			String gender = node.get("gender").asText();
			
			if(gender.equalsIgnoreCase("FEMININO") || gender.equalsIgnoreCase("MASCULINO")) {
			}else {
				apiError.setCode("json_filter");
				apiError.setDescription("Genero(gender) inválido, considere: 'Feminino' ou 'Masculino'.");
				
				return apiError;
			}
		}*/
		return customerPojo;
	}
}