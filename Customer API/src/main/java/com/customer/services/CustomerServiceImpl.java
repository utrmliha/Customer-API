package com.customer.services;

import java.util.List;

import com.customer.dao.DaoCustomer;
import com.customer.dto.Customer;
import com.customer.error.MappingException;
import com.customer.filter.CustomerFilter;
import com.customer.pojo.CustomerPojo;
import com.customer.validation.CustomerFilterValidation;
import com.customer.validation.CustomerValidation;
import com.google.inject.Inject;

import spark.Request;
import spark.Response;

public class CustomerServiceImpl implements CustomerService{
	
	@Inject
	private CustomerValidation customerValidation;
	@Inject
	private CustomerFilterValidation customerFilterValidation;
	@Inject
	private CustomerFilterService customerFilterService;
	@Inject
	private DaoCustomer daoCustomer;
	
	@Override
	public Customer criar(Request request, Response response) throws MappingException{
		
		CustomerPojo customerPojo = null;

		customerPojo = customerValidation.validate(request);

		Customer customer;
		if((customer = daoCustomer.salvar(customerPojo)) != null) {
			response.type("application/json");
			return customer;
		}else {
			throw new MappingException("Cpf já cadastrado.");
		}
	}
	
	@Override
	public List<Customer> listar(Request request, Response response) throws MappingException{
		List<Customer> customers = null;
		CustomerFilter customerFilter = null;
		
		if((customerFilter = customerFilterValidation.validate(request)) == null) {
			customers = daoCustomer.listar();
		}else {
			String sql = customerFilterService.montarSqlComFiltro(customerFilter);
			customers = daoCustomer.listarComFiltro(sql);
		}
		
		if(customers != null && customers.size() > 0) {
			response.type("application/json");
			return customers;
		}else {
			throw new MappingException("Customers não encontrados.");
		}
	}

	@Override
	public Customer buscar(Request request, Response response) throws MappingException{
		Long id = Long.parseLong(request.params("id"));
		Customer customer = daoCustomer.buscar(id);
		
		if(customer != null) {
			response.type("application/json");
			return customer;
		}else {
			throw new MappingException("Customer não encontrado.");
		}
	}
	
	@Override
	public Customer atualizar(Request request, Response response) throws MappingException{
		Long id = Long.parseLong(request.params("id"));
		CustomerPojo customerPojo = null;
		
		customerPojo = customerValidation.validate(request);
		customerPojo.setId(id);
		
		Customer customer;
		if((customer = daoCustomer.atualizar(customerPojo)) != null) {
			response.type("application/json");
			return customer;
		}else {
			throw new MappingException("Customer não encontrado.");
		}
	}

	@Override
	public Customer deletar(Request request, Response response) throws MappingException{
		Long id = Long.parseLong(request.params("id"));

		if(daoCustomer.deletar(id)) {
			throw new MappingException("Customer deletado com sucesso!");
		}else {
			throw new MappingException("Customer não encontrado.");
		}
	}

	

}
