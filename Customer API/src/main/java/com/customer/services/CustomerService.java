package com.customer.services;

import java.io.IOException;
import java.util.List;

import com.customer.dto.Customer;
import com.customer.filter.CustomerFilter;
import com.customer.pojo.CustomerPojo;

import spark.Request;
import spark.Response;

public interface CustomerService {
	
	public Customer criar(Request request, Response response);
	
	public List<Customer> listar(Request request, Response response);
	
	public Customer buscar(Request request, Response response);
	
	public Customer atualizar(Request request, Response response);
	
	public Customer deletar(Request request, Response response);
		
}
