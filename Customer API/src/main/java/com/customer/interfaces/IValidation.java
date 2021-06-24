package com.customer.interfaces;

import java.io.IOException;

import com.customer.dto.ApiError;

public interface IValidation {
	public ApiError validate(String requestBody) throws IOException;
}
