package com.customer.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.customer.filter.CustomerFilter;
import com.customer.services.JsonService;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.google.inject.Inject;

import spark.Request;

public class CustomerFilterValidationImpl implements CustomerFilterValidation {

	@Inject
	JsonService jsonService;
	
	@Override
	public CustomerFilter validate(Request request){
		CustomerFilter customerFilter = null;
		try {
			customerFilter = jsonService.fromJson(request.body(), CustomerFilter.class);
		}catch (Exception e) {
			if(e instanceof MismatchedInputException) {
				return null;
			}else {
				e.printStackTrace();//ERRO JSON INVÁLIDO
			}
		}
		
		Pattern regexPattern = null;
        Matcher regMatcher = null;
        
        if(customerFilter.getBirthDate() != null) {
        	regexPattern = Pattern.compile("^((?:(?:1[6-9]|2[0-9])\\d{2})(-)(?:(?:(?:0[13578]|1[02])(-)31)|((0[1,3-9]|1[0-2])(-)(29|30))))$|^(?:(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00)))(-)02(-)29)$|^(?:(?:1[6-9]|2[0-9])\\d{2})(-)(?:(?:0[1-9])|(?:1[0-2]))(-)(?:0[1-9]|1\\d|2[0-8])$");
        	regMatcher = regexPattern.matcher(customerFilter.getBirthDate());
        	
        	if(!regMatcher.matches()) {
        		
        		System.out.println("Data de nascimento(birthDate) inválida, considere o formato ex: '1980-12-28'.");
        		
        		return null;
        	}
        }
        if(customerFilter.getSortBy() != null) {
        	regexPattern = Pattern.compile("CUSTOMER_NAME|CUSTOMER_BIRTH_DATE|CUSTOMER_CREATED_AT|ADDRESS_STATE|ADDRESS_CITY");
        	regMatcher = regexPattern.matcher(customerFilter.getSortBy() );
        	if(!regMatcher.matches()) {
        		System.out.println("Ordenar por(sortBy) inválido, considere um destes valores: 'CUSTOMER_NAME, CUSTOMER_BIRTH_DATE, CUSTOMER_CREATED_AT, ADDRESS_STATE ou ADDRESS_CITY'.");

        		return null;
        	}
        }
        if(customerFilter.getSortOrder() != null) {
        	regexPattern = Pattern.compile("ASC|DESC");
        	regMatcher = regexPattern.matcher(customerFilter.getSortOrder());
        	if(!regMatcher.matches()) {
        		System.out.println("ordem de classificação(sortOrder) inválido, considere um destes valores: 'ASC ou DESC'.");

        		return null;
        	}
        }
		
		return customerFilter;
	}
	
}
