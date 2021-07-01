package com.customer.services;

import java.util.ArrayList;
import java.util.List;

import org.jdbi.v3.core.locator.ClasspathSqlLocator;

import com.customer.filter.AddressFilter;

public class AddressFilterServiceImpl implements AddressFilterService{

	@Override
	public String montarSqlComFiltro(AddressFilter addressFilter) {
		//==================Personalizando a Query
		String Query = ClasspathSqlLocator.findSqlOnClasspath("com.customer.sql.select-filteredAddresses");
		List<String> filtrarPor = new ArrayList<String>();
		
		if(addressFilter.getId() == null || addressFilter.getId() < 1) {
		}else {
			filtrarPor.add("id = "+ addressFilter.getId());
		}
		if(addressFilter.getState() == null || addressFilter.getState().isBlank()) {
		}else {
			filtrarPor.add("state = "+ addressFilter.getState());
		}
		if(addressFilter.getCity() == null || addressFilter.getCity().isBlank()) {
		}else {
			filtrarPor.add("city LIKE '%"+ addressFilter.getCity()+ "%'");
		}
		if(addressFilter.getNeighborhood() == null || addressFilter.getNeighborhood().isBlank()) {
		}else {
			filtrarPor.add("neighborhood LIKE '%"+ addressFilter.getNeighborhood()+ "%'");
		}
		if(addressFilter.getZipCode() == null || addressFilter.getZipCode().isBlank()) {
		}else {
			filtrarPor.add("zipCode = "+ addressFilter.getZipCode());
		}
		if(addressFilter.getStreet() == null || addressFilter.getStreet().isBlank()) {
		}else {
			filtrarPor.add("street LIKE '%"+ addressFilter.getStreet()+"%'");
		}
		if(addressFilter.getNumber() == null || addressFilter.getNumber().isBlank()) {
		}else {
			filtrarPor.add("number = "+ addressFilter.getNumber());
		}
		if(addressFilter.getAdditionalInformation() == null || addressFilter.getAdditionalInformation().isBlank()) {
		}else {
			filtrarPor.add("additionalInformation LIKE '%"+ addressFilter.getAdditionalInformation()+ "%'");
		}
		if(addressFilter.isMain()) {
			filtrarPor.add("main = "+ addressFilter.isMain());
		}
		if(addressFilter.getSortBy() == null || addressFilter.getSortBy().isBlank()) {
			filtrarPor.add("ORDER BY id ");
		}
		else if(!(addressFilter.getSortBy() == null) || !addressFilter.getSortBy().isBlank()) {
			String sortBy = null;
			if(addressFilter.getSortBy().equalsIgnoreCase("ID"))
				sortBy = "id";
			else if(addressFilter.getSortBy().equalsIgnoreCase("STATE"))
				sortBy = "state";
			else if(addressFilter.getSortBy().equalsIgnoreCase("CITY"))
				sortBy = "city";
			else if(addressFilter.getSortBy().equalsIgnoreCase("NEIGHBORHOOD"))
				sortBy = "neighborhood";
			else if(addressFilter.getSortBy().equalsIgnoreCase("ZIPCODE"))
				sortBy = "zipCode";
			else if(addressFilter.getSortBy().equalsIgnoreCase("STREET"))
				sortBy = "street";
			else if(addressFilter.getSortBy().equalsIgnoreCase("NUMBER"))
				sortBy = "number";
			else if(addressFilter.getSortBy().equalsIgnoreCase("ADDITIONALINFORMATION"))
				sortBy = "additionalInformation";
			else if(addressFilter.getSortBy().equalsIgnoreCase("MAIN"))
				sortBy = "main";
			filtrarPor.add("ORDER BY "+ sortBy);
		}
		if(addressFilter.getSortOrder() == null || addressFilter.getSortOrder().isBlank()) {
			filtrarPor.add("ASC");
			
		}else{
			filtrarPor.add(addressFilter.getSortOrder());
		}
		
		//==================Verificando a nescessidade de AND
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
		
		return Query;
	}

}
