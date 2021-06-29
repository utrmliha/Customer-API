package com.customer.dao;

import com.customer.dto.Customer;
import com.customer.pojo.CustomerPojo;

public interface DaoCustomer {
	public Customer save(CustomerPojo customerPojo);
}
