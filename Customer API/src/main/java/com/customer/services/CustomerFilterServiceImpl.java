package com.customer.services;

import java.util.ArrayList;
import java.util.List;

import org.jdbi.v3.core.locator.ClasspathSqlLocator;

import com.customer.filter.CustomerFilter;

public class CustomerFilterServiceImpl implements CustomerFilterService{

	@Override
	public String montarSqlComFiltro(CustomerFilter customerFilter) {
		//==================Personalizando a Query
		String Query = ClasspathSqlLocator.findSqlOnClasspath("com.customer.sql.select-filterCustomer");
		List<String> filtrarPor = new ArrayList<String>();

		if(customerFilter.getName() == null || customerFilter.getName().isBlank()) {
		}else {
			filtrarPor.add("name LIKE '%"+ customerFilter.getName()+"%'");
		}
		if(customerFilter.getBirthDate() == null || customerFilter.getBirthDate().isBlank()) {
		}else {
			filtrarPor.add("birthDate = '"+ customerFilter.getBirthDate()+ "'");
		}
		if(customerFilter.getState() == null || customerFilter.getState().isBlank()) {
		}else {
			filtrarPor.add("state = '"+ customerFilter.getState()+ "'");
		}
		if(customerFilter.getCity() == null || customerFilter.getCity().isBlank()) {
		}else {
			filtrarPor.add("city LIKE '%"+ customerFilter.getCity()+ "%'");
		}
		if(customerFilter.getSortBy() == null || customerFilter.getSortBy().isBlank()) {
			filtrarPor.add("ORDER BY name ");
		}
		else if(!(customerFilter.getSortBy() == null) || !customerFilter.getSortBy().isBlank()) {
			String sortBy = null;
			if(customerFilter.getSortBy().equalsIgnoreCase("CUSTOMER_NAME"))
				sortBy = "name";
			else if(customerFilter.getSortBy().equalsIgnoreCase("CUSTOMER_BIRTH_DATE"))
				sortBy = "birthDate";
			else if(customerFilter.getSortBy().equalsIgnoreCase("CUSTOMER_CREATED_AT"))
				sortBy = "createdAt";
			else if(customerFilter.getSortBy().equalsIgnoreCase("ADDRESS_STATE"))
				sortBy = "state";
			else if(customerFilter.getSortBy().equalsIgnoreCase("ADDRESS_CITY"))
				sortBy = "city";
			filtrarPor.add("ORDER BY "+ sortBy);
		}
		if(customerFilter.getSortOrder() == null || customerFilter.getSortOrder().isBlank()) {
			filtrarPor.add("ASC");

		}else{
			filtrarPor.add(customerFilter.getSortOrder());
		}

		//==================Verificando a nescessidade de AND e WHERE
		for(int i = 0; i < filtrarPor.size(); i++) {
			if(i == (filtrarPor.size()-2)) {
				Query += filtrarPor.get(i);				
			}else {
				if(i >= (filtrarPor.size() - 3)) {
					Query += filtrarPor.get(i) + " ";
				}else {
					Query += filtrarPor.get(i)+ " AND ";	
				}
			}
		}
		if(filtrarPor.size() == 2) {
			Query = Query.replace(" WHERE ", " ");
		}

		return Query;
	}

}
