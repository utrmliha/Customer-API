package com.customer.validation;

import com.customer.error.MappingException;
import com.customer.pojo.AddressPojo;

import spark.Request;

public interface AddressValidation {
	public AddressPojo validate(Request request) throws MappingException;
}
