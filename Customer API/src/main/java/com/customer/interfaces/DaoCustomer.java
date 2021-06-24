package com.customer.interfaces;

import java.io.IOException;
import java.util.List;

import com.customer.dto.ApiError;
import com.customer.dto.Customer;
import com.customer.filter.CustomerFilter;
import com.customer.pojo.CustomerPojo;

public interface DaoCustomer {
	
	public ApiError Create(CustomerPojo customerPojo);
	
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
