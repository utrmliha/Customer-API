package com.customer;

import com.customer.dto.Address;
import com.customer.dto.Customer;
import com.customer.filter.AddressFilter;
import com.customer.filter.CustomerFilter;
import com.customer.module.ApplicationModule;
import com.customer.pojo.AddressPojo;
import com.customer.pojo.CustomerPojo;
import com.customer.services.AddressService;
import com.customer.services.ApiError;
import com.customer.services.CustomerService;
import com.customer.services.CustomerServiceImpl;
import com.customer.services.JsonService;
import com.customer.services.JsonServiceImpl;
import com.customer.validation.AddressFilterValidationImpl;
import com.customer.validation.AddressValidationImpl;
import com.customer.validation.CustomerFilterValidationImpl;
import com.customer.validation.CustomerValidationImpl;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

import spark.Spark;

import static spark.Spark.*;

import java.util.List;

import org.eclipse.jetty.util.log.Log;

public class Application {
	CustomerValidationImpl customerValidation;
	AddressValidationImpl addressValidation;
	CustomerPojo customerPojo;
	CustomerService customerService;
	AddressService daoAddress;
	JsonService jsonService;
	
	@Inject
	public Application(CustomerValidationImpl customerValidation,
						AddressValidationImpl addressValidation,
						CustomerPojo customerPojo,
						CustomerService customerService,
						AddressService daoAddress,
						JsonService jsonService) {

		this.customerValidation = customerValidation;
		this.addressValidation = addressValidation;
		this.customerPojo = customerPojo;
		this.customerService = customerService;
		this.daoAddress = daoAddress;
		this.jsonService = jsonService;
	}

	public void run() {
		port(8080);
		post("/customers", this.customerService::Create, this.jsonService::toJson);
	}
	
	public static void main(String[] args) {
		
		Injector injector = Guice.createInjector(new ApplicationModule());
		Application application = injector.getInstance(Application.class);
		application.run();
		
		/*
		port(8080);
		
		//=================================================INICIO (CUSTOMER)=======================================================//
		post("/customers", (request, response) -> {
			response.type("application/json");
			
			CustomerValidation customerValidation = new CustomerValidation();
			AddressValidation addressValidation = new AddressValidation();
			ApiError apiError;
			
			if(((apiError = customerValidation.validate(request.body())) != null) || 
			   ((apiError = addressValidation.validate(request.body())) != null)) {
				response.status(400);
				return JsonParsing.ObjectToJson(apiError);
			}else {
				
				CustomerPojo customerPojo = JsonParsing.StringToObject(request.body(), CustomerPojo.class);
				customerPojo.setAddress(JsonParsing.JsonToObject(JsonParsing.StringToJson(request.body()).findValue("address"), AddressPojo.class));
				
				Injector injector = Guice.createInjector(new DaoCustomerModule());
				DaoCustomer daoCustomer = injector.getInstance(DaoCustomer.class);
				
				if((apiError = daoCustomer.Create(customerPojo)) != null) {
					response.status(409);
					return JsonParsing.ObjectToJson(apiError);
				}
				response.status(201);
				return JsonParsing.ObjectToJson(daoCustomer.FindByCpf(customerPojo.getCpf()));
			}
		});
		
		get("/customers", (request, response) ->{
			response.type("application/json");
			
			Injector injector = Guice.createInjector(new DaoCustomerModule());
			DaoCustomer daoCustomer = injector.getInstance(DaoCustomer.class);
			CustomerFilter customerFilter = new CustomerFilter();
			ApiError apiError;
			
			if(!request.body().isBlank()) {
				CustomerFilterValidation customerFilterValidation = new CustomerFilterValidation();
				
				if((apiError = customerFilterValidation.validate(request.body())) != null) {
					response.status(400);
					return JsonParsing.ObjectToJson(apiError);
				}
				
				try {
					customerFilter = JsonParsing.StringToObject(request.body(), CustomerFilter.class);
				}catch (Exception e) {
					if(e instanceof UnrecognizedPropertyException) {
						apiError = new ApiError();
						response.status(400);
						apiError.setCode("json_invalid");
						apiError.setDescription("Falha, Json do corpo da requisição Inválido.");
						return JsonParsing.ObjectToJson(apiError);
					}
				}
				List<Customer> customers = daoCustomer.FindAllWithFilter(customerFilter);
				
				if(customers.size() < 1) {
					apiError = new ApiError();
					apiError.setCode("result_empty");
					response.status(404);
					apiError.setDescription("Customers não encontrados!");
					return JsonParsing.ObjectToJson(apiError);
					
				}else{
					response.status(200);
					return JsonParsing.ObjectToJson(customers);
				}
			}
			List<Customer> customers = daoCustomer.FindAllNoFilter();
			
			if(customers.size() < 1) {
				apiError = new ApiError();
				apiError.setCode("result_empty");
				response.status(404);
				apiError.setDescription("Customers não encontrados!");
				return JsonParsing.ObjectToJson(apiError);
				
			}else{
				response.status(200);
				return JsonParsing.ObjectToJson(customers);
			}
		});
		
		get("/customers/:id", (request, response) -> {
			response.type("application/json");
			
			Injector injector = Guice.createInjector(new DaoCustomerModule());
			DaoCustomer daoCustomer = injector.getInstance(DaoCustomer.class);
			Customer customer;
			
			if((customer = daoCustomer.FindById(Integer.parseInt(request.params("id")))) == null) {
				ApiError apiError = new ApiError();
				apiError.setCode("result_empty");
				response.status(404);
				apiError.setDescription("Cliente não encontrato!");
				
				return JsonParsing.ObjectToJson(apiError);
			}
			
			response.status(200);
		    return JsonParsing.ObjectToJson(customer);
		});

		put("/customers/:id", (request, response) -> {
			response.type("application/json");
			
			CustomerValidation customerValidation = new CustomerValidation();
			AddressValidation addressValidation = new AddressValidation();
			ApiError apiError;
			CustomerPojo customerPojo;
			
			if(addressValidation.ExistAddressOnCustomer(request.body())) {
				if((apiError = addressValidation.validate(request.body())) != null) {
					response.status(400);
					return JsonParsing.ObjectToJson(apiError);
				}else {
					Injector injector = Guice.createInjector(new DaoCustomerModule());
					DaoCustomer daoCustomer = injector.getInstance(DaoCustomer.class);
					
					customerPojo = JsonParsing.StringToObject(request.body(), CustomerPojo.class);
					customerPojo.setId(Integer.parseInt(request.params("id")));
					
					if((apiError = daoCustomer.Update(customerPojo)) != null) {
						response.status(404);
						return JsonParsing.ObjectToJson(apiError);
					}else {
						response.status(200);
						return JsonParsing.ObjectToJson(daoCustomer.FindById(customerPojo.getId()));
					}
				}
			
			}else {
				if((apiError = customerValidation.validate(request.body())) != null) {
					response.status(400);
					return JsonParsing.ObjectToJson(apiError);
				}else {
					customerPojo = JsonParsing.StringToObject(request.body(), CustomerPojo.class);
					customerPojo.setId(Integer.parseInt(request.params("id")));
					DaoCustomerImpl daoCustomer = new DaoCustomerImpl();
					if((apiError = daoCustomer.Update(customerPojo)) != null) {
						response.status(404);
						return JsonParsing.ObjectToJson(apiError);
					}else {
						response.status(200);
						return JsonParsing.ObjectToJson(daoCustomer.FindById(customerPojo.getId()));
					}
				}
			}
		});
		
		delete("/customers/:id", (request, response) -> {
			response.type("application/json");
			
			Injector injector = Guice.createInjector(new DaoCustomerModule());
			DaoCustomer daoCustomer = injector.getInstance(DaoCustomer.class);
			ApiError apiError;
			
			if((apiError = daoCustomer.DeleteById(Integer.parseInt(request.params("id")))) != null) {
				response.status(404);
				return JsonParsing.ObjectToJson(apiError);
			}
			
			response.status(204);
			return "Customer removido com sucesso!";
		});
		//=================================================FIM (CUSTOMER)=======================================================//
		//================================================INICIO (ADDRESS)======================================================//
		post("/customers/:id/addresses", (request, response) -> {
			response.type("application/json");
			
			ApiError apiError;
			AddressValidation addressValidation = new AddressValidation();
			
			if((apiError = addressValidation.validate(request.body())) != null){
				
				Injector injector = Guice.createInjector(new DaoAddressModule());
				DaoAddress daoAddress = injector.getInstance(DaoAddress.class);
				
				AddressPojo addressPojo = JsonParsing.StringToObject(request.body(), AddressPojo.class);
				addressPojo.setCustomer_id(Integer.parseInt(request.params("id")));
				
				if((apiError = daoAddress.Create(addressPojo)) != null) {
					response.status(400);
					return JsonParsing.ObjectToJson(apiError);
				}else{
					response.status(201);
					return JsonParsing.ObjectToJson(daoAddress.FindExactAddress(addressPojo));
				}
			}else {
				response.status(400);
				return JsonParsing.ObjectToJson(apiError);
			}
		});
		
		get("/customers/:id/addresses", (request, response) -> {
			response.type("application/json");
			
			AddressFilterValidation addressFilterValidation = new AddressFilterValidation();
			Injector injector = Guice.createInjector(new DaoAddressModule());
			DaoAddress daoAddress = injector.getInstance(DaoAddress.class);
			ApiError apiError;
			
			if(!request.body().isBlank()) {
				if((apiError = addressFilterValidation.validate(request.body())) != null) {
					response.status(400);
					return JsonParsing.ObjectToJson(apiError);
				}else {
					AddressFilter addressFilter = JsonParsing.StringToObject(request.body(), AddressFilter.class);
					response.status(200);
					return JsonParsing.ObjectToJson(daoAddress.FindAllWithFilter(addressFilter, Integer.parseInt(request.params("id"))));
				}
			}else {
				return JsonParsing.ObjectToJson(daoAddress.FindAllNoFilter(Integer.parseInt(request.params("id"))));
			}
		});
		
		get("/customers/:id/addresses/:address_id", (request, response) -> {
			response.type("application/json");
			
			Injector injector = Guice.createInjector(new DaoAddressModule());
			DaoAddress daoAddress = injector.getInstance(DaoAddress.class);
			ApiError apiError;
			Address address;

			if((address = daoAddress.FindAddressUser(Integer.parseInt(request.params("address_id")), Integer.parseInt(request.params("id")))) == null) {
				apiError = new ApiError();
				apiError.setCode("find_address");
				apiError.setDescription("Address não encontrado");
				response.status(404);
				return JsonParsing.ObjectToJson(apiError);
			}else {
				response.status(200);
				return JsonParsing.ObjectToJson(daoAddress.FindAddressUser(Integer.parseInt(request.params("address_id")), Integer.parseInt(request.params("id"))));
			}

		});
		
		put("/customers/:id/addresses/:address_id", (request, response) -> {
			response.type("application/json");
			
			Injector injector = Guice.createInjector(new DaoAddressModule());
			DaoAddress daoAddress = injector.getInstance(DaoAddress.class);
			AddressValidation addressValidation = new AddressValidation();
			ApiError apiError;

			if((apiError = addressValidation.validate(request.body())) == null) {
				
				AddressPojo addressPojo = JsonParsing.StringToObject(request.body(), AddressPojo.class);
				addressPojo.setId(Integer.parseInt(request.params("address_id")));
				addressPojo.setCustomer_id(Integer.parseInt(request.params("id")));
				
				if((apiError = daoAddress.Update(addressPojo)) != null) {
					response.status(404);
					return JsonParsing.ObjectToJson(apiError);
				}else {
					response.status(200);
					return JsonParsing.ObjectToJson(daoAddress.FindAddressUser(Integer.parseInt(request.params("address_id")), Integer.parseInt(request.params("id"))));
				}
			}else {
				apiError = new ApiError();
				apiError.setCode("json_format");
				apiError.setDescription("Falha, Json do corpo da requisição Inválido.");
				response.status(400);
				return JsonParsing.ObjectToJson(apiError);
			}
		});
		
		delete("/customers/:id/addresses/:address_id", (request, response) -> {
			response.type("application/json");
			
			Injector injector = Guice.createInjector(new DaoAddressModule());
			DaoAddress daoAddress = injector.getInstance(DaoAddress.class);
			ApiError apiError;
			
			if((apiError = daoAddress.DeleteAddressCustomer(Integer.parseInt(request.params("id")), Integer.parseInt(request.params("address_id")))) != null) {
				response.status(404);
				return JsonParsing.ObjectToJson(apiError);
			}
			
			response.status(204);
			return "Customer removido com sucesso!";
		});
		
		//==================================================FIM (ADDRESS)=======================================================//
		
		internalServerError((request, response) -> {
			response.type("application/json");
		    ApiError apiError = new ApiError();
		    apiError.setCode("error");
		    apiError.setDescription("Erro Interno do Servidor!");
		    return JsonParsing.ObjectToJson(apiError);
		});
		*/
	}

}
