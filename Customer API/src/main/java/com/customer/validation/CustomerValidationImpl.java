package com.customer.validation;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.customer.error.MappingException;
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
	public CustomerPojo validate(Request request) throws MappingException{
		CustomerPojo customerPojo = null;
		
		try {
			customerPojo = jsonService.fromJson(request.body(), CustomerPojo.class);
		}catch (Exception e) {
			if(e instanceof MismatchedInputException) {
				//Se não tiver nada no body, ignora
				return null;
			}else {
				throw new MappingException("Formato do Json inválido.");
			}
		}
		
		
		Pattern regexPattern = null;
        Matcher regMatcher = null;

        if(customerPojo.getAddress() == null && !(request.requestMethod() == "PUT")) {
        	throw new MappingException("Json inválido, Informe um Address.");
        }else if(customerPojo.getName() == null || customerPojo.getName().isBlank()) {
			throw new MappingException("Nome do cliente é um campo obrigatório.");
		}else{
			regexPattern = Pattern.compile("([0-9])");
	        regMatcher = regexPattern.matcher(customerPojo.getName());
	        
	        if(regMatcher.matches()) {
	        	throw new MappingException("Nome(name) do cliente inválido.");
	        }
		}
		if(customerPojo.getEmail() == null || customerPojo.getEmail().isBlank()) {
			throw new MappingException("Email(email) do cliente é um campo obrigatório.");
		}else {
			regexPattern = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
	        regMatcher = regexPattern.matcher(customerPojo.getEmail());
	        
	        if(!regMatcher.matches()) {
				throw new MappingException("Email(email) inválido, considere o formato ex: 'email@email.com'.");
	        }
		}
		if(customerPojo.getBirthDate() == null || customerPojo.getBirthDate().isBlank()) {
			throw new MappingException("Data de nascimento(birthDate) do cliente é um campo obrigatório.");
		
		}else {
			regexPattern = Pattern.compile("^((?:(?:1[6-9]|2[0-9])\\d{2})(-)(?:(?:(?:0[13578]|1[02])(-)31)|((0[1,3-9]|1[0-2])(-)(29|30))))$|^(?:(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00)))(-)02(-)29)$|^(?:(?:1[6-9]|2[0-9])\\d{2})(-)(?:(?:0[1-9])|(?:1[0-2]))(-)(?:0[1-9]|1\\d|2[0-8])$");
	        regMatcher = regexPattern.matcher(customerPojo.getBirthDate());
	        
	        if(!regMatcher.matches()) {
				throw new MappingException("Data de nascimento(birthDate) inválida, considere o formato ex: '1980-12-28'.");
	        }else {
	        	int dia = Integer.parseInt(customerPojo.getBirthDate().split("-")[2]);
	        	int mes = Integer.parseInt(customerPojo.getBirthDate().split("-")[1].split("-")[0]);
	        	int ano = Integer.parseInt(customerPojo.getBirthDate().split("-")[0]);
	        	int idade = LocalDate.now().minus(Period.of(ano, mes, dia)).getYear();
	        	
	        	if(idade > 100) {
					throw new MappingException("Data de nascimento(birthDate) inválida.");
	        	}
	        }
		}
		if(customerPojo.getCpf() == null || customerPojo.getCpf().isBlank()) {
			throw new MappingException("Cpf(cpf) do cliente é um campo obrigatório.");
			
		}else {
			regexPattern = Pattern.compile("[0-9]{3}[0-9]{3}[0-9]{3}[0-9]{2}");
	        regMatcher = regexPattern.matcher(customerPojo.getCpf());
	        
	        if(regMatcher.matches()) {
				throw new MappingException("CPF(cpf) inválido, considere o formato ex: '756.684.987-69'.");
				
	        }else{
				regexPattern = Pattern.compile("^[0-9]{3}[\\.][0-9]{3}[\\.][0-9]{3}[-][0-9]{2}$");
				Matcher regMatcher2 = regexPattern.matcher(customerPojo.getCpf());
				
		        if(!regMatcher2.matches()) {
					throw new MappingException("CPF(cpf) inválido.");
					
		        }
		    }
		}
		if(customerPojo.getGender() == null || customerPojo.getGender().isBlank()) {
			throw new MappingException("Gênero(gender) do cliente é um campo obrigatório.");
			
		}else {
			String gender = customerPojo.getGender();
			
			if(gender.equalsIgnoreCase("FEMININO") || gender.equalsIgnoreCase("MASCULINO")) {
			}else {
				throw new MappingException("Genero(gender) inválido, considere: 'Feminino' ou 'Masculino'.");
				
			}
		}
		return customerPojo;
	}
}