package com.customer.validation;

import java.io.IOException;

import com.customer.dto.Address;
import com.customer.pojo.AddressPojo;

import spark.Request;

public interface AddressValidation {
	public AddressPojo validate(Request request) throws IOException;
}
