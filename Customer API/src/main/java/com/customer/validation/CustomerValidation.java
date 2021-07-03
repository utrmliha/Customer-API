package com.customer.validation;

import com.customer.error.MappingException;
import com.customer.pojo.CustomerPojo;

import spark.Request;

public interface CustomerValidation {
	public CustomerPojo validate (Request request) throws MappingException;
}
