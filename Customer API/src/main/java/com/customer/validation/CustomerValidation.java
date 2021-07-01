package com.customer.validation;

import java.io.IOException;

import com.customer.pojo.CustomerPojo;

import spark.Request;

public interface CustomerValidation {
	public CustomerPojo validate (Request request) throws IOException;
}
