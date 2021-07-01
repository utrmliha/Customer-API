package com.customer.services;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.locator.ClasspathSqlLocator;
import org.jdbi.v3.core.mapper.reflect.ConstructorMapper;

import com.customer.dao.DaoCustomer;
import com.customer.dto.Address;
import com.customer.dto.Customer_InnerJoin_Address;
import com.customer.dto.Customer;
import com.customer.filter.CustomerFilter;
import com.customer.jdbi.MySQLConnectionImpl;
import com.customer.pojo.CustomerPojo;
import com.customer.validation.CustomerFilterValidation;
import com.customer.validation.CustomerValidation;
import com.google.inject.Inject;

import spark.Request;
import spark.Response;

import java.time.format.DateTimeFormatter;

public class CustomerServiceImpl implements CustomerService{
	
	@Inject
	private CustomerValidation customerValidation;
	@Inject
	private CustomerFilterValidation customerFilterValidation;
	@Inject
	private CustomerFilterService customerFilterService;
	@Inject
	private Jdbi jdbi;
	@Inject
	private DaoCustomer daoCustomer;
	
	@Override
	public Customer criar(Request request, Response response){
		CustomerPojo customerPojo = null;
		try {
			customerPojo = customerValidation.validate(request);
		}catch (IOException e) {
			e.printStackTrace();
			return null;//RETORNA ERROR CUSTOMER NAO ENCONTRADO
		}
		response.status(404);
		return daoCustomer.salvar(customerPojo);
	}
	
	@Override
	public List<Customer> listar(Request request, Response response) {
		List<Customer> customers = null;
		CustomerFilter customerFilter = null;
		
		if((customerFilter = customerFilterValidation.validate(request)) == null) {
			customers = daoCustomer.listar();
		}else {
			String sql = customerFilterService.montarSqlComFiltro(customerFilter);
			customers = daoCustomer.listarComFiltro(sql);
		}
		
		if(customers != null && customers.size() > 0) {
			return customers;
		}else {
			return null;//RETORNA ERROR CUSTOMERS NAO ENCONTRADOS
		}
	}

	@Override
	public Customer buscar(Request request, Response response) {
		Long id = Long.parseLong(request.params("id"));
		Customer customer = daoCustomer.buscar(id);
		
		if(customer != null) {
			return customer;
		}else {
			return null;//RETORNA ERROR CUSTOMERS NAO ENCONTRADOS
		}
	}
	
	@Override
	public Customer atualizar(Request request, Response response) {
		Long id = Long.parseLong(request.params("id"));
		CustomerPojo customerPojo = null;
		
		try {
			customerPojo = customerValidation.validate(request);
			customerPojo.setId(id);
		}catch (IOException e) {
			e.printStackTrace();
			//RETORNA ERROR JSON INVÁLIDO
		}
		
		boolean exist = daoCustomer.customerExiste(customerPojo.getId());
		if(exist) {
			return daoCustomer.atualizar(customerPojo);
		}else {
			return null;//RETORNA ERROR CUSTOMER NAO ENCONTRADO
		}
	}

	@Override
	public Customer deletar(Request request, Response response) {

		Long id = Long.parseLong(request.params("id"));
		boolean exist = daoCustomer.customerExiste(id);
		if(exist) {
			daoCustomer.deletar(id);
			return null;//RETORNA SUCESSO
		}else {
			return null;//RETORNA ERROR CUSTOMER NAO ENCONTRADO
		}
	}

	

}
