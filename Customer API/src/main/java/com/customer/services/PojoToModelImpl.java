package com.customer.services;

import com.customer.dto.Address;
import com.customer.dto.Customer;
import com.customer.pojo.AddressPojo;
import com.customer.pojo.CustomerPojo;
import com.google.inject.Inject;

public class PojoToModelImpl implements PojoToModel{
	
	@Override
	public Customer Customer(CustomerPojo customerPojo) {
		Customer customer = new Customer();
		customer.setId(customerPojo.getId());
		customer.setName(customerPojo.getName());
		customer.setEmail(customerPojo.getEmail());
		customer.setBirthDate(customerPojo.getBirthDate());
		customer.setCpf(customerPojo.getCpf());
		customer.setGender(customerPojo.getGender());
		
		if(customerPojo.getAddress() != null) {
			if(customerPojo.getAddress().getMain()) {
				customer.setMainAddress(Address(customerPojo.getAddress()));
			}else {
				customer.getAdresses().add(Address(customerPojo.getAddress()));
			}

		}
		return customer;
	}
	
	@Override
	public Address Address(AddressPojo addressPojo) {
		Address address = new Address();
		address.setId(addressPojo.getId());
		address.setState(addressPojo.getState());
		address.setCity(addressPojo.getCity());
		address.setNeighborhood(addressPojo.getNeighborhood());
		address.setZipCode(addressPojo.getZipCode());
		address.setStreet(addressPojo.getStreet());
		address.setNumber(addressPojo.getNumber());
		address.setAdditionalInformation(addressPojo.getAdditionalInformation());
		address.setMain(addressPojo.getMain());
		
		return address;
	}

}
