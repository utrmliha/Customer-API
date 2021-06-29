package com.customer.services;

import com.customer.dto.Address;
import com.customer.dto.Customer;
import com.customer.pojo.AddressPojo;
import com.customer.pojo.CustomerPojo;

public interface PojoToModel {
	public Customer Customer(CustomerPojo customerPojo);
	public Address Address(AddressPojo addressPojo);
}
