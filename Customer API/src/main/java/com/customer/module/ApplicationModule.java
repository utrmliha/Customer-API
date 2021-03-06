package com.customer.module;

import org.jdbi.v3.core.Jdbi;

import com.customer.dao.DaoAddress;
import com.customer.dao.DaoAddressImpl;
import com.customer.dao.DaoCustomer;
import com.customer.dao.DaoCustomerImpl;
import com.customer.jdbi.MySQLConnectionImpl;
import com.customer.services.AddressFilterService;
import com.customer.services.AddressFilterServiceImpl;
import com.customer.services.AddressService;
import com.customer.services.AddressServiceImpl;
import com.customer.services.CustomerFilterService;
import com.customer.services.CustomerFilterServiceImpl;
import com.customer.services.CustomerService;
import com.customer.services.CustomerServiceImpl;
import com.customer.services.ErrorMappingService;
import com.customer.services.ErrorMappingServiceImpl;
import com.customer.services.JsonService;
import com.customer.services.JsonServiceImpl;
import com.customer.validation.AddressFilterValidation;
import com.customer.validation.AddressFilterValidationImpl;
import com.customer.validation.AddressValidation;
import com.customer.validation.AddressValidationImpl;
import com.customer.validation.CustomerFilterValidation;
import com.customer.validation.CustomerFilterValidationImpl;
import com.customer.validation.CustomerValidation;
import com.customer.validation.CustomerValidationImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public class ApplicationModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(CustomerValidation.class).to(CustomerValidationImpl.class);
		bind(CustomerFilterValidation.class).to(CustomerFilterValidationImpl.class);
		
		bind(AddressValidation.class).to(AddressValidationImpl.class);
		bind(AddressFilterValidation.class).to(AddressFilterValidationImpl.class);
		
		bind(CustomerService.class).to(CustomerServiceImpl.class);
		bind(CustomerFilterService.class).to(CustomerFilterServiceImpl.class);
		
		bind(AddressService.class).to(AddressServiceImpl.class);
		bind(AddressFilterService.class).to(AddressFilterServiceImpl.class);
		
		bind(JsonService.class).to(JsonServiceImpl.class);
		bind(ErrorMappingService.class).to(ErrorMappingServiceImpl.class);
		
		bind(DaoCustomer.class).to(DaoCustomerImpl.class).in(Singleton.class);
		bind(DaoAddress.class).to(DaoAddressImpl.class).in(Singleton.class);
		
	}
	
	@Provides
	@Singleton
	public ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		return mapper;
	}
	
	@Provides
	@Singleton
	public Jdbi jdbi() {
		MySQLConnectionImpl mySQLConnectionImpl = new MySQLConnectionImpl();
		return mySQLConnectionImpl.conectar("jdbc:mysql://localhost:3306/mydbcustomers", "root", "admin");
	}
}
