package com.customer.validation;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		
		
		Pattern regexPattern = null;
        Matcher regMatcher = null;

		if(customerPojo.getName() == null || customerPojo.getName().isBlank()) {
			System.out.println("Nome(name) do cliente é um campo obrigatório.");
		}else{
			regexPattern = Pattern.compile("([0-9])");
	        regMatcher = regexPattern.matcher(customerPojo.getName());
	        
	        if(regMatcher.matches()) {
	        	//apiError.setCode("json_filter");
	        	System.out.println("Nome(name) do cliente inválido.");
	        	
	        	return null;
	        }
		}
		if(customerPojo.getEmail() == null || customerPojo.getEmail().isBlank()) {
			System.out.println("Email(email) do cliente é um campo obrigatório.");
			
			return null;
		}else {
			regexPattern = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
	        regMatcher = regexPattern.matcher(customerPojo.getEmail());
	        
	        if(!regMatcher.matches()) {
				System.out.println("Email(email) inválido, considere o formato ex: 'email@email.com'.");
				
				return null;
	        }
		}
		if(customerPojo.getBirthDate() == null || customerPojo.getBirthDate().isBlank()) {
			System.out.println("Data de nascimento(birthDate) do cliente é um campo obrigatório.");
			
			return null;
		}else {
			regexPattern = Pattern.compile("^((?:(?:1[6-9]|2[0-9])\\d{2})(-)(?:(?:(?:0[13578]|1[02])(-)31)|((0[1,3-9]|1[0-2])(-)(29|30))))$|^(?:(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00)))(-)02(-)29)$|^(?:(?:1[6-9]|2[0-9])\\d{2})(-)(?:(?:0[1-9])|(?:1[0-2]))(-)(?:0[1-9]|1\\d|2[0-8])$");
	        regMatcher = regexPattern.matcher(customerPojo.getBirthDate());
	        
	        if(!regMatcher.matches()) {
				System.out.println("Data de nascimento(birthDate) inválida, considere o formato ex: '1980-12-28'.");
				
				return null;
	        }else {
	        	int dia = Integer.parseInt(customerPojo.getBirthDate().split("-")[2]);
	        	int mes = Integer.parseInt(customerPojo.getBirthDate().split("-")[1].split("-")[0]);
	        	int ano = Integer.parseInt(customerPojo.getBirthDate().split("-")[0]);
	        	int idade = LocalDate.now().minus(Period.of(ano, mes, dia)).getYear();
	        	
	        	if(idade > 100) {
					System.out.println("Data de nascimento(birthDate) inválida.");
	        	}
	        }
		}
		if(customerPojo.getCpf() == null || customerPojo.getCpf().isBlank()) {
			System.out.println("Cpf(cpf) do cliente é um campo obrigatório.");
			
			return null;
		}else {
			regexPattern = Pattern.compile("[0-9]{3}[0-9]{3}[0-9]{3}[0-9]{2}");
	        regMatcher = regexPattern.matcher(customerPojo.getCpf());
	        
	        if(regMatcher.matches()) {
				System.out.println("CPF(cpf) inválido, considere o formato ex: '756.684.987-69'.");
				
				return null;
	        }else{
				regexPattern = Pattern.compile("^[0-9]{3}[\\.][0-9]{3}[\\.][0-9]{3}[-][0-9]{2}$");
				Matcher regMatcher2 = regexPattern.matcher(customerPojo.getCpf());
				
		        if(!regMatcher2.matches()) {
					System.out.println("CPF(cpf) inválido.");
					
					return null;
		        }
		    }
		}
		if(customerPojo.getGender() == null || customerPojo.getGender().isBlank()) {
			System.out.println("Gênero(gender) do cliente é um campo obrigatório.");
			
			return null;
		}else {
			String gender = customerPojo.getGender();
			
			if(gender.equalsIgnoreCase("FEMININO") || gender.equalsIgnoreCase("MASCULINO")) {
			}else {
				System.out.println("Genero(gender) inválido, considere: 'Feminino' ou 'Masculino'.");
				
				return null;
			}
		}
		return customerPojo;
	}
}