package com.customer.module;

import com.customer.dao.DaoAddressImpl;
import com.customer.interfaces.DaoAddress;
import com.google.inject.AbstractModule;

public class DaoAddressModule extends AbstractModule{

	@Override
	protected void configure() {
		bind(DaoAddress.class).to(DaoAddressImpl.class);
	}
}
