package com.customer.services;

import com.customer.dto.Address;
import com.customer.dto.Customer;
import com.customer.pojo.AddressPojo;
import com.customer.pojo.CustomerPojo;

public class PojoToModel {
	
	public static Customer Customer(CustomerPojo customerPojo) {
		Customer customer = new Customer(customerPojo.getId(), null, customerPojo.getName(), customerPojo.getEmail(), 
				customerPojo.getBirthDate(), customerPojo.getCpf(), customerPojo.getGender(), null, null);
		if(customerPojo.getAddress() != null) {
			Address address = new Address(customerPojo.getAddress().getId(), customerPojo.getAddress().getState(), customerPojo.getAddress().getCity(), customerPojo.getAddress().getNeighborhood(), 
					customerPojo.getAddress().getZipCode(), customerPojo.getAddress().getStreet(), customerPojo.getAddress().getNumber(), customerPojo.getAddress().getAdditionalInformation(), customerPojo.getAddress().getMain());
			customer.setMainAddress(address);
		}
		return customer;
	}
	
	public static Address Address(AddressPojo addressPojo) {
		Address address = new Address(addressPojo.getId(), addressPojo.getState(), addressPojo.getCity(), addressPojo.getNeighborhood(), 
				addressPojo.getZipCode(), addressPojo.getStreet(), addressPojo.getNumber(), addressPojo.getAdditionalInformation(), addressPojo.getMain());
		
		return address;
	}

}
