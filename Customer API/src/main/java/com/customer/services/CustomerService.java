package com.customer.services;

import java.io.IOException;
import java.util.List;

import com.customer.dto.Customer;
import com.customer.filter.CustomerFilter;
import com.customer.pojo.CustomerPojo;

import spark.Request;
import spark.Response;

public interface CustomerService {
	
	public Customer Create(Request request, Response response);
	
	public ApiError Update(CustomerPojo customerPojo);
	
	public ApiError DeleteById(int id);
	
	public boolean ExistCustomerMainAddress(int customer_id);
	
	public boolean ExistCustomerAddress(int customer_id);
	
	public boolean ExistById(int id);
	
	public boolean ExistByCpf(String cpf);
	
	public List<Customer> FindAllWithFilter(CustomerFilter customerFilter) throws IOException;
	
	public List<Customer> FindAllNoFilter();
	
	public Customer FindById(int id);
	
	public Customer FindByCpf(String cpf);

}
