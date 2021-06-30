package com.customer.validation;

import com.customer.filter.CustomerFilter;

import spark.Request;

public interface CustomerFilterValidation {
	public CustomerFilter validate(Request request);
}
