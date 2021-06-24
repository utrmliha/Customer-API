package com.customer.interfaces;

import java.io.IOException;

import com.customer.dto.ApiError;

public interface Validation {
	public ApiError validate(String requestBody) throws IOException;
}
