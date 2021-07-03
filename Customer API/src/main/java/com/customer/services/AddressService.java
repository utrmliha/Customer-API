package com.customer.services;

import java.util.List;

import com.customer.dto.Address;
import com.customer.error.MappingException;

import spark.Request;
import spark.Response;

public interface AddressService {
	
	public Address criar(Request request, Response response) throws MappingException;
	
	public List<Address> listar(Request request, Response response) throws MappingException;
	
	public Address buscar(Request request, Response response) throws MappingException;
	
	public Address atualizar(Request request, Response response) throws MappingException;
	
	public Address deletar(Request request, Response response) throws MappingException;
}
