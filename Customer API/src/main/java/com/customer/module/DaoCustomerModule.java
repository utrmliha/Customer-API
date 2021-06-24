package com.customer.module;

import com.customer.dao.DaoCustomerImpl;
import com.customer.interfaces.DaoCustomer;
import com.google.inject.AbstractModule;

public class DaoCustomerModule extends AbstractModule{
	@Override
	protected void configure() {
		bind(DaoCustomer.class).to(DaoCustomerImpl.class);
	}

}
