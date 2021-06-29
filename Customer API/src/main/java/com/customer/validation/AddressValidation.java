package com.customer.validation;

import java.io.IOException;

import com.customer.dto.Address;

import spark.Request;

public interface AddressValidation {
	public Address validate(Request request) throws IOException;
}
