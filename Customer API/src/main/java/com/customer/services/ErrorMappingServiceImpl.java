package com.customer.services;

import com.customer.dto.Error;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.inject.Inject;

import spark.Response;

public class ErrorMappingServiceImpl implements ErrorMappingService{

	@Inject
	private JsonService jsonService;
	
	@Override
	public Response throwException(Exception exception, Response response) {
		response.type("application/json");
		
		Error error = new Error();

		if(exception.getMessage().contains("inválid") ||
				exception.getMessage().contains("obrigatório")) {
			response.status(400);
			error.setCode("json_invalid");
		}else if(exception.getMessage().contains("deletado com sucesso")) {
			response.status(200);
			error.setCode("success");
		}else if(exception.getMessage().equalsIgnoreCase("Customers não encontrados.")) {
			response.status(404);
			error.setCode("customers_empty");
		}else if(exception.getMessage().equalsIgnoreCase("Customer não encontrado.")) {
			response.status(404);
			error.setCode("customer_empty");
		}else if(exception.getMessage().equalsIgnoreCase("Address não encontrados.")) {
			response.status(404);
			error.setCode("Address_empty");
		}else if(exception.getMessage().equalsIgnoreCase("Adresses não encontrado.")) {
			response.status(404);
			error.setCode("Adresses_empty");
		}else if (exception.getMessage().equalsIgnoreCase("Cpf já cadastrado.")) {
			response.status(409);
			error.setCode("customer_cpf");
		}
		
		error.setDescription(exception.getMessage());

		try {
			response.body(jsonService.toJson(error));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return response;
	}
}
