package com.customer.validation;

import com.customer.dto.CustomerFilter;

import spark.Request;

public interface CustomerFilterValidation {
	public CustomerFilter validate(Request request);
}
