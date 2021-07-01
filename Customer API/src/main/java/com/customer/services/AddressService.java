package com.customer.services;

import java.util.List;

import com.customer.dto.Address;
import com.customer.dto.Customer;
import com.customer.filter.AddressFilter;
import com.customer.pojo.AddressPojo;

import spark.Request;
import spark.Response;

public interface AddressService {
	
	public Address criar(Request request, Response response);
	
	public List<Address> listar(Request request, Response response);
	
	public Address buscar(Request request, Response response);
	
	public Address atualizar(Request request, Response response);
	
	public Address deletar(Request request, Response response);
}
