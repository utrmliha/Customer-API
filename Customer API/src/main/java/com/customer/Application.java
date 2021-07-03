package com.customer;

import com.customer.dao.DaoAddress;
import com.customer.dao.DaoCustomer;
import com.customer.error.MappingException;
import com.customer.module.ApplicationModule;
import com.customer.services.AddressFilterService;
import com.customer.services.AddressService;
import com.customer.services.CustomerFilterService;
import com.customer.services.CustomerService;
import com.customer.services.ErrorMappingService;
import com.customer.services.JsonService;
import com.customer.validation.AddressFilterValidation;
import com.customer.validation.AddressValidation;
import com.customer.validation.CustomerFilterValidation;
import com.customer.validation.CustomerValidation;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

import spark.ExceptionHandler;

import static spark.Spark.*;

public class Application {
	CustomerValidation customerValidation;
	CustomerFilterValidation customerFilterValidation;
	
	AddressValidation addressValidation;
	AddressFilterValidation addressFilterValidation;
	
	CustomerService customerService;
	CustomerFilterService customerFilterService;
	
	AddressService addressService;
	AddressFilterService addressFilterService;
	
	JsonService jsonService;
	ErrorMappingService errorMappingService;
	
	DaoCustomer daoCustomer;
	DaoAddress daoAddress;
	
	@Inject
	public Application(CustomerValidation customerValidation,
						CustomerFilterValidation customerFilterValidation,
						AddressValidation addressValidation,
						AddressFilterValidation addressFilterValidation,
						CustomerService customerService,
						CustomerFilterService customerFilterService,
						AddressService addressService,
						AddressFilterService addressFilterService,
						JsonService jsonService,
						ErrorMappingService errorMappingService,
						DaoCustomer daoCustomer,
						DaoAddress daoAddress) {

		this.customerValidation = customerValidation;
		this.customerFilterValidation = customerFilterValidation;
		this.addressValidation = addressValidation;
		this.addressFilterValidation = addressFilterValidation;
		this.customerService = customerService;
		this.customerFilterService = customerFilterService;
		this.addressService = addressService;
		this.addressFilterService = addressFilterService;
		this.jsonService = jsonService;
		this.errorMappingService = errorMappingService;
		this.daoCustomer = daoCustomer;
		this.daoAddress = daoAddress;
	}

	
	@SuppressWarnings("unchecked")
	public void run() {
		port(8080);
		
		//===============CUSTOMER
		post("/customers", this.customerService::criar, this.jsonService::toJson);//
		get("/customers", this.customerService::listar, this.jsonService::toJson);//
		get("/customers/:id", this.customerService::buscar, this.jsonService::toJson);//
		put("/customers/:id", this.customerService::atualizar, this.jsonService::toJson);//
		delete("/customers/:id", this.customerService::deletar, this.jsonService::toJson);//
		
		//===============ADDRESS
		post("/customers/:id/addresses", this.addressService::criar, this.jsonService::toJson);//
		get("/customers/:id/addresses", this.addressService::listar, this.jsonService::toJson);//
		get("/customers/:id/addresses/:address_id", this.addressService::buscar, this.jsonService::toJson);//
		put("/customers/:id/addresses/:address_id", this.addressService::atualizar, this.jsonService::toJson);
		delete("/customers/:id/addresses/:address_id", this.addressService::deletar, this.jsonService::toJson);//
		
		//===============Error
		exception(MappingException.class, exceptionHandler());
	}
	
	@SuppressWarnings("rawtypes")
	private ExceptionHandler exceptionHandler() {
	    return (exception, request, response) -> {
	    	errorMappingService.throwException(exception, response);
	    };
	}
	
	public static void main(String[] args) {
		System.out.println("Iniciando Aplicação...");
		
		Injector injector = Guice.createInjector(new ApplicationModule());
		Application application = injector.getInstance(Application.class);
		
		application.run();
		
		System.out.println("Aplicação Iniciada com sucesso!");
	}
	
}