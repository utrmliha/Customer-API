package com.customer.services;

import spark.Response;

public interface ErrorMappingService {
	
	public Response throwException(Exception exception, Response response);

}
