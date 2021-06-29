package com.customer.validation;

import java.io.IOException;

import com.customer.dto.Customer;
import com.customer.pojo.CustomerPojo;
import com.customer.services.ApiError;

import spark.Request;

public interface CustomerValidation {
	public CustomerPojo validate(Request request) throws IOException;
}
