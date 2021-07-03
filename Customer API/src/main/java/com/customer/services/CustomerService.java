package com.customer.services;

import java.util.List;

import com.customer.dto.Customer;
import com.customer.error.MappingException;

import spark.Request;
import spark.Response;

public interface CustomerService {
	
	public Customer criar(Request request, Response response) throws MappingException;
	
	public List<Customer> listar(Request request, Response response) throws MappingException;
	
	public Customer buscar(Request request, Response response) throws MappingException;
	
	public Customer atualizar(Request request, Response response) throws MappingException;
	
	public Customer deletar(Request request, Response response) throws MappingException;
		
}
