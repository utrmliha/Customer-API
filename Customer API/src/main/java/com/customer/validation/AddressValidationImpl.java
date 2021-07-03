package com.customer.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.customer.error.MappingException;
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
	public AddressPojo validate(Request request) throws MappingException {
		AddressPojo addressPojo = null;
		
		try {
			addressPojo = jsonService.fromJson(request.body(), AddressPojo.class);
		}catch (Exception e) {
			if(e instanceof MismatchedInputException) {
				//Se n�o tiver nada no body, ignora
				return null;
			}else {
				throw new MappingException("Formato do Json inv�lido.");
			}
		}	
		
		Pattern regexPattern = null;
        Matcher regMatcher = null;
        
		if(addressPojo.getState() == null || addressPojo.getState().isBlank()) {
			throw new MappingException("Estado(state) � um campo obrigat�rio.");
			
		}else{
			regexPattern = Pattern.compile("AC|AL|AP|AM|BA|CE|DF|ES|GO|MA|MT|MS|MG|PA|PB|PR|PE|PI|RJ|RN|RS|RO|RR|SC|SP|SE|TO");
	        regMatcher = regexPattern.matcher(addressPojo.getState());
			if(!regMatcher.matches()) {
				throw new MappingException("Estado(state) inv�lido, considere o formato ex: 'SP'.");
				
			}
		}
		if(addressPojo.getCity() == null || addressPojo.getCity().isBlank()) {
			throw new MappingException("Cidade(city) � um campo obrigat�rio.");
			
		}
		if(addressPojo.getNeighborhood() == null || addressPojo.getNeighborhood().isBlank()) {
			throw new MappingException("Bairro(neighborhood) � um campo obrigat�rio.");
			
		}
		if(addressPojo.getZipCode() == null || addressPojo.getZipCode().isBlank()) {
			throw new MappingException("Cep(zipCode) � um campo obrigat�rio.");
			
		}else {
			regexPattern = Pattern.compile("[0-9]{5}[-][0-9]{3}");
	        regMatcher = regexPattern.matcher(addressPojo.getZipCode());
	       
	        if(!regMatcher.matches()) {
				throw new MappingException("Cep(zipCode) inv�lido, considere o formato ex: '06432-444'.");
				
	        }
		}
		if(addressPojo.getStreet() == null || addressPojo.getStreet().isBlank()) {
			throw new MappingException("Rua(street) � um campo obrigat�rio.");
			
		}
		if(addressPojo.getNumber() == null) {
			}else {
				regexPattern = Pattern.compile("\\d+");
				regMatcher = regexPattern.matcher(addressPojo.getNumber());
				
				if(!regMatcher.matches()) {
					throw new MappingException("Numero(number) deve ser somente n�meros.");
					
				}
		}
		if(addressPojo.getMain() == null) {
			throw new MappingException("Endere�o principal(main) � um campo obrigat�rio.");
			
		}else if(addressPojo.getMain() == true || addressPojo.getMain() == false){
		}else {
			throw new MappingException("Endere�o principal(main) inv�lido, considere 'true' ou 'false'.");
			
		}
		return addressPojo;
				
	}
	
}
