package com.customer.validation;

import com.customer.dto.AddressFilter;

import spark.Request;

public interface AddressFilterValidation {
	public AddressFilter validate(Request request);
}
