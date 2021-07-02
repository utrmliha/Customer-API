package com.customer.validation;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.customer.filter.AddressFilter;
import com.customer.services.JsonService;
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
				e.printStackTrace();//ERRO JSON INV�LIDO
			}
		}
		
		Pattern regexPattern = null;
        Matcher regMatcher = null;
        
        if(addressFilter.getId() != null) {
        	regexPattern = Pattern.compile("\\d+");
        	regMatcher = regexPattern.matcher(addressFilter.getId());
        	
        	if(!regMatcher.matches()) {
        		System.out.println("Id deve ser somente números.");
        		
        		return null;
        	}
        }
        if(addressFilter.getState() != null) {
			regexPattern = Pattern.compile("AC|AL|AP|AM|BA|CE|DF|ES|GO|MA|MT|MS|MG|PA|PB|PR|PE|PI|RJ|RN|RS|RO|RR|SC|SP|SE|TO");
			regMatcher = regexPattern.matcher(addressFilter.getState());
			
			if(!regMatcher.matches()) {
				System.out.println("Estado(state) inválido, considere o formato ex: 'SP'.");
				
				return null;
			}
		}
        if(addressFilter.getZipCode() != null) {
			regexPattern = Pattern.compile("[0-9]{5}[-][0-9]{3}");
	        regMatcher = regexPattern.matcher(addressFilter.getZipCode());
	       
	        if(!regMatcher.matches()) {
				System.out.println("Cep(zipCode) inválido, considere o formato ex: '06432-444'.");
				
				return null;
	        }
		}
        if(addressFilter.getNumber() != null) {
			regexPattern = Pattern.compile("\\d+");
			regMatcher = regexPattern.matcher(addressFilter.getNumber());
			
			if(!regMatcher.matches()) {
				System.out.println("Numero(number) deve ser somente números.");
				
				return null;
			}
		}
        if(addressFilter.getMain().equalsIgnoreCase("true") || addressFilter.getMain().equalsIgnoreCase("false")){
		}else {
			System.out.println("Endereço principal(main) inválido, considere 'true' ou 'false'.");
			
			return null;
		}
        if(addressFilter.getSortBy() != null) {
        	regexPattern = Pattern.compile("ID|STATE|CITY|NEIGHBORHOOD|ZIPCODE|STREET|NUMBER|ADDITIONALINFORMATION|MAIN");
        	regMatcher = regexPattern.matcher(addressFilter.getSortBy());
        	if(!regMatcher.matches()) {
        		System.out.println("Ordenar por(sortBy) inválido, considere um destes valores: 'ID, STATE, CITY, NEIGHBORHOOD, ZIPCODE, STREET, NUMBER, ADDITIONALINFORMATION ou MAIN'.");
        			
        		return null;
        	}
        }
        if(addressFilter.getSortOrder() != null) {
        	regexPattern = Pattern.compile("ASC|DESC");
        	regMatcher = regexPattern.matcher(addressFilter.getSortOrder());
        	if(!regMatcher.matches()) {
        		System.out.println("ordem de classificação(sortOrder) inválido, considere um destes valores: 'ASC ou DESC'.");
        		
        		return null;
        	}
        }
		return addressFilter;
	}

}
