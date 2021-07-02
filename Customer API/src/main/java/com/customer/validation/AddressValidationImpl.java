package com.customer.validation;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.customer.pojo.AddressPojo;
import com.customer.services.JsonService;
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
		
		Pattern regexPattern = null;
        Matcher regMatcher = null;
        
		if(addressPojo.getState() == null || addressPojo.getState().isBlank()) {
			System.out.println("Estado(state) � um campo obrigat�rio.");
			
			return null;
		}else{
			regexPattern = Pattern.compile("AC|AL|AP|AM|BA|CE|DF|ES|GO|MA|MT|MS|MG|PA|PB|PR|PE|PI|RJ|RN|RS|RO|RR|SC|SP|SE|TO");
	        regMatcher = regexPattern.matcher(addressPojo.getState());
			if(!regMatcher.matches()) {
				//apiError.setCode("json_filter");
				System.out.println("Estado(state) inv�lido, considere o formato ex: 'SP'.");
				
				return null;
			}
		}
		if(addressPojo.getCity() == null || addressPojo.getCity().isBlank()) {
			System.out.println("Cidade(city) � um campo obrigat�rio.");
			
			return null;
		}
		if(addressPojo.getNeighborhood() == null || addressPojo.getNeighborhood().isBlank()) {
			System.out.println("Bairro(neighborhood) � um campo obrigat�rio.");
			
			return null;
		}
		if(addressPojo.getZipCode() == null || addressPojo.getZipCode().isBlank()) {
			System.out.println("Cep(zipCode) � um campo obrigat�rio.");
			
			return null;
		}else {
			regexPattern = Pattern.compile("[0-9]{5}[-][0-9]{3}");
	        regMatcher = regexPattern.matcher(addressPojo.getZipCode());
	       
	        if(!regMatcher.matches()) {
				System.out.println("Cep(zipCode) inv�lido, considere o formato ex: '06432-444'.");
				
				return null;
	        }
		}
		if(addressPojo.getStreet() == null || addressPojo.getStreet().isBlank()) {
			System.out.println("Rua(street) � um campo obrigat�rio.");
			
			return null;
		}
		if(addressPojo.getNumber() == null) {
			}else {
				regexPattern = Pattern.compile("\\d+");
				regMatcher = regexPattern.matcher(addressPojo.getNumber());
				
				if(!regMatcher.matches()) {
					System.out.println("Numero(number) deve ser somente n�meros.");
					
					return null;
				}
		}
		if(addressPojo.getMain() == null) {
			System.out.println("Endere�o principal(main) � um campo obrigat�rio.");
			
			return null;
		}else if(addressPojo.getMain() == true || addressPojo.getMain() == false){
		}else {
			System.out.println("Endere�o principal(main) inv�lido, considere 'true' ou 'false'.");
			
			return null;
		}
		return addressPojo;
				
	}
	
}
