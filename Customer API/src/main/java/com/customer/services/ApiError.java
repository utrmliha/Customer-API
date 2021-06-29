package com.customer.services;

public class ApiError extends Exception{
	
	public ApiError(String message) {
		super(message);
	}
}
