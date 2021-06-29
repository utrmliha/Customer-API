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
import com.customer.dto.AddressCustomer_InnerJoin;
import com.customer.dto.Customer;
import com.customer.filter.CustomerFilter;
import com.customer.jdbi.MySQLConnectionImpl;
import com.customer.pojo.CustomerPojo;
import com.customer.validation.CustomerValidation;
import com.google.inject.Inject;

import spark.Request;
import spark.Response;

import java.time.format.DateTimeFormatter;

public class CustomerServiceImpl implements CustomerService{
	
	private Handle handle;//EXCLUIR
	@Inject
	private CustomerValidation customerValidation;
	@Inject
	private Jdbi jdbi;
	@Inject
	private DaoCustomer daoCustomer;
	
	@Override
	public Customer Create(Request request, Response response){
		CustomerPojo customerPojo = null;
		try {
			customerPojo = customerValidation.validate(request);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(customerPojo != null) {
			return daoCustomer.save(customerPojo);
		}
		return new Customer();
		/*
		Customer customer = PojoToModel.Customer(customerPojo);
			
		if(!ExistByCpf(customer.getCpf())) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			
			customer.setUpdateAt(LocalDateTime.now().format(formatter));
			customer.setCreatedAt(customer.getUpdateAt());
			
			String sql = ClasspathSqlLocator.findSqlOnClasspath("com.customer.sql.insert-customer");
			
			int idCustomerCriado = handle.createUpdate(sql)
				.bind("name", customer.getName())
				.bind("email", customer.getEmail())
				.bind("birthDate", customer.getBirthDate())
				.bind("cpf", customer.getCpf())
				.bind("gender", customer.getGender())
				.bind("createdAt", customer.getCreatedAt())
				.bind("updateAt", customer.getUpdateAt())
				.executeAndReturnGeneratedKeys("id")
				.mapTo(int.class)
				.one();
			
			sql = ClasspathSqlLocator.findSqlOnClasspath("com.customer.sql.insert-address");
			handle.createUpdate(sql)
			.bind("state", customer.getMainAddress().getState())
			.bind("city", customer.getMainAddress().getCity())
			.bind("neighborhood", customer.getMainAddress().getNeighborhood())
			.bind("zipCode", customer.getMainAddress().getZipCode())
			.bind("street", customer.getMainAddress().getStreet())
			.bind("number", customer.getMainAddress().getNumber())
			.bind("additionalInformation", customer.getMainAddress().getAdditionalInformation())
			.bind("main", customer.getMainAddress().getMain())
			.bind("customer_id", idCustomerCriado)
			.execute();
						
			
			return null;
		}else {
			ApiError apiError = new ApiError();
			apiError.setCode("create_customer");
			apiError.setDescription("Cpf já cadastrado!");
			
			return apiError;
		}
		*/
	}
	
	@Override
	public ApiError Update(CustomerPojo customerPojo) {
		//Customer customer = PojoToModelImpl.Customer(customerPojo);
		Customer customer = new Customer();		
		
		ApiError apiError;
				/*
		if(ExistById(customer.getId())) {
			if(customer.getMainAddress() != null) {
				if(customer.getMainAddress().getMain()) {
					handle.createUpdate("UPDATE address SET state = ?, city = ?, neighborhood = ?, zipCode = ?, "
							+ "street = ?, number = ?, additionalInformation = ?, main = ? WHERE customer_id = ? and main = 1")
					.bind(0, customer.getMainAddress().getState())
					.bind(1, customer.getMainAddress().getCity())
					.bind(2, customer.getMainAddress().getNeighborhood())
					.bind(3, customer.getMainAddress().getZipCode())
					.bind(4, customer.getMainAddress().getStreet())
					.bind(5, customer.getMainAddress().getNumber())
					.bind(6, customer.getMainAddress().getAdditionalInformation())
					.bind(7, customer.getMainAddress().getMain())
					.bind(8, customer.getId())
					.execute();
					
					
					return null;
				}
			}else {
				if(!ExistByCpf(customer.getCpf())) {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
					customer.setUpdateAt(LocalDateTime.now().format(formatter));
					
					handle.createUpdate("UPDATE customer SET name = ?, email = ?, birthDate = ?, cpf = ?, gender = ?, updateAt = ? WHERE id =  ?")
					.bind(0, customer.getName())
					.bind(1, customer.getEmail())
					.bind(2, customer.getBirthDate())
					.bind(3, customer.getCpf())
					.bind(4, customer.getGender())
					.bind(5, customer.getUpdateAt())
					.bind(6, customer.getId())
					.execute();
					
					
					return null;
				}else {
					apiError = null;
					apiError.setCode("update_customer");
					apiError.setDescription("Cpf já cadastrado!");
					
					return apiError;
				}
			}
		}else {
			apiError = null;
			apiError.setCode("update_customer");
			apiError.setDescription("Customer nao encontrado!");
			
			return apiError;
		}*/
		return null;
	}

	@Override
	public ApiError DeleteById(int id) {

		if(ExistById(id)) {
			if(ExistCustomerMainAddress(id)) {
				handle.execute("DELETE FROM address WHERE customer_id = "+id+" and main = true");
			}if (ExistCustomerAddress(id)) {
				handle.execute("DELETE FROM address WHERE customer_id = "+id+" and main = false");
			}
			
			handle.execute("DELETE FROM customer WHERE id = "+id);
			return null;

		}else {
			ApiError apiError = null;/*
			apiError.setCode("delete_customer");
			apiError.setDescription("Customer não encontrado");*/
			return apiError;
		}
	}
	
	@Override
	public boolean ExistCustomerMainAddress(int customer_id) {
		boolean exist = handle.createQuery("SELECT IF(EXISTS(SELECT id FROM address WHERE customer_id = ? AND main = 1), 1, 0)")
			.bind(0, customer_id)
			.mapTo(boolean.class)
			.one();
		return exist;
	}
	
	@Override
	public boolean ExistCustomerAddress(int customer_id) {
		boolean exist = handle.createQuery("SELECT IF(EXISTS(SELECT id FROM address WHERE customer_id = ? AND main = 0), 1, 0)")
			.bind(0, customer_id)
			.mapTo(boolean.class)
			.one();
		return exist;
	}
	
	@Override
	public boolean ExistById(int id) {
		boolean exist =	handle.createQuery("SELECT IF(EXISTS(SELECT id FROM customer WHERE id = ?), 1, 0)")
				.bind(0, id).mapTo(boolean.class).one();
		
		return exist;
	}

	@Override
	public boolean ExistByCpf(String cpf) {
		boolean exist =	handle.createQuery("SELECT IF(EXISTS(SELECT id FROM customer WHERE cpf = ?), 1, 0)")
				.bind(0, cpf).mapTo(boolean.class).one();
		
		return exist;
	}
	
	@Override
	public List<Customer> FindAllWithFilter(CustomerFilter customerFilter) throws IOException{
		
		//===============================INICIO- Personalizando a Query======================================//
		
		String Query = "SELECT c.id as c_id, a.id as a_id FROM customer c INNER JOIN address a on a.customer_id = c.id WHERE ";
		List<String> filtrarPor = new ArrayList<String>();
		
		if(customerFilter.getName() == null || customerFilter.getName().isBlank()) {
		}else {
			filtrarPor.add("name LIKE '%"+ customerFilter.getName()+"%'");
		}
		if(customerFilter.getBirthDate() == null || customerFilter.getBirthDate().isBlank()) {
		}else {
			filtrarPor.add("birthDate = '"+ customerFilter.getBirthDate()+ "'");
		}
		if(customerFilter.getState() == null || customerFilter.getState().isBlank()) {
		}else {
			filtrarPor.add("state = '"+ customerFilter.getState()+ "'");
		}
		if(customerFilter.getCity() == null || customerFilter.getCity().isBlank()) {
		}else {
			filtrarPor.add("city LIKE '%"+ customerFilter.getCity()+ "%'");
		}
		if(customerFilter.getSortBy() == null || customerFilter.getSortBy().isBlank()) {
			filtrarPor.add("ORDER BY name ");
		}
		else if(!(customerFilter.getSortBy() == null) || !customerFilter.getSortBy().isBlank()) {
			String sortBy = null;
			if(customerFilter.getSortBy().equalsIgnoreCase("CUSTOMER_NAME"))
				sortBy = "name";
			else if(customerFilter.getSortBy().equalsIgnoreCase("CUSTOMER_BIRTH_DATE"))
				sortBy = "birthDate";
			else if(customerFilter.getSortBy().equalsIgnoreCase("CUSTOMER_CREATED_AT"))
				sortBy = "createdAt";
			else if(customerFilter.getSortBy().equalsIgnoreCase("ADDRESS_STATE"))
				sortBy = "state";
			else if(customerFilter.getSortBy().equalsIgnoreCase("ADDRESS_CITY"))
				sortBy = "city";
			filtrarPor.add("ORDER BY "+ sortBy);
		}
		if(customerFilter.getSortOrder() == null || customerFilter.getSortOrder().isBlank()) {
			filtrarPor.add("ASC");
			
		}else{
			filtrarPor.add(customerFilter.getSortOrder());
		}
		//===============================FIM- Personalizando a Query======================================//
		
		//=====================INICIO- Verificando a nescessidade de AND e WHERE==========================//
		for(int i = 0; i < filtrarPor.size(); i++) {
			if(i == (filtrarPor.size()-2)) {
				Query += filtrarPor.get(i);				
			}else {
				if(i >= (filtrarPor.size() - 3)) {
					Query += filtrarPor.get(i) + " ";
				}else {
					Query += filtrarPor.get(i)+ " AND ";	
				}
			}
		}
		if(filtrarPor.size() == 2) {
			Query = Query.replace(" WHERE ", " ");
		}
		//======================FIM- Verificando a nescessidade de AND e WHERE===========================//
		
		//Pegando a ordem dos resultados da busca personalizada anterior
		
		List<AddressCustomer_InnerJoin> ACInnerJoin = null;
		
		handle.registerRowMapper(ConstructorMapper.factory(AddressCustomer_InnerJoin.class));
		if((ACInnerJoin = handle.createQuery(Query).mapTo(AddressCustomer_InnerJoin.class).list()) == null) {
			return null;
		}
		
		//============================INICIO- Montando a lista de Customer===============================//
		List<Customer> customers = new ArrayList<Customer>();
		int lastCustomerId   = 0;
		int indexCustomer = -1;
		
		for(int i = 0; i < ACInnerJoin.size(); i++) {//
			
			if(ACInnerJoin.get(i).getC_id() != lastCustomerId) {
				
				handle.registerRowMapper(ConstructorMapper.factory(Customer.class));
				Customer customerTemp = handle.createQuery("SELECT * FROM customer WHERE id = "+ACInnerJoin.get(i).getC_id())
						.mapTo(Customer.class).one();
				
				handle.registerRowMapper(ConstructorMapper.factory(Address.class));
				Address addresstemp  = handle.createQuery("SELECT * FROM address WHERE id = " +ACInnerJoin.get(i).getA_id())
						.mapTo(Address.class).one();
				
				if(addresstemp.getMain()) {
					customerTemp.setMainAddress(addresstemp);
				}else if(!addresstemp.getMain()) {
					customerTemp.getAdresses().add(addresstemp);
				}
				
				customers.add(customerTemp);
				indexCustomer++;
				lastCustomerId = ACInnerJoin.get(i).getC_id();
			}else {
				
				handle.registerRowMapper(ConstructorMapper.factory(Address.class));
				Address addresstemp = handle.createQuery("SELECT * FROM address WHERE id = "+ACInnerJoin.get(i).getA_id())
								.mapTo(Address.class).one();
				
				if(addresstemp.getMain()) {
					customers.get(indexCustomer).setMainAddress(addresstemp);
				}else if(!addresstemp.getMain()) {
					customers.get(indexCustomer).getAdresses().add(addresstemp);
				}
				
			}

		}
		//============================FIM- Montando a lista de Customer===============================//
		
		return customers;
	}
	
	@Override
	public List<Customer> FindAllNoFilter(){
		return null;
		/*
		List<Customer> customer;
		String ResultExist = "SELECT IF(EXISTS(SELECT id FROM customer), 1, 0)"; 
		
		if(!handle.createQuery(ResultExist).mapTo(boolean.class).one())
			return customer = new ArrayList<Customer>();
		
		handle.registerRowMapper(ConstructorMapper.factory(Customer.class));
		customer = handle.createQuery("SELECT * FROM customer")
			.mapTo(Customer.class).list();

		for (int i = 0; i < customer.size(); i++) {
			
			int idCustomer = customer.get(i).getId();
			Address mainAddress = null;
			List<Address> addresses = null;
			 
			if(ExistCustomerMainAddress(idCustomer)) {
				handle.registerRowMapper(ConstructorMapper.factory(Address.class));
				mainAddress = handle.createQuery("SELECT * FROM address WHERE customer_id = ? AND main = 1")
						.bind(0, idCustomer)
						.mapTo(Address.class)
						.one();	
			}
			
			if(ExistCustomerAddress(idCustomer)) {
				handle.registerRowMapper(ConstructorMapper.factory(Address.class));
				addresses = handle.createQuery("SELECT * FROM address WHERE customer_id = ? AND main = 0")
						.bind(0, idCustomer)
						.mapTo(Address.class)
						.list();
			}
			
			customer.get(i).setAdresses(addresses);
			customer.get(i).setMainAddress(mainAddress);
			
		}
		
		
		return customer;*/
	}
	
	@Override
	public Customer FindById(int id) {
		Customer customer;
				
		if(ExistById(id)) {
			handle.registerRowMapper(ConstructorMapper.factory(Customer.class));
			customer = handle.createQuery("SELECT * FROM customer WHERE ID = ?")
					.bind(0, id).mapTo(Customer.class).one();
			
			handle.registerRowMapper(ConstructorMapper.factory(Address.class));
			List<Address> addresses = handle.createQuery("SELECT * FROM address WHERE customer_id = ?")
					.bind(0, id).mapTo(Address.class).list();
			
			for (Address address : addresses) {
				if(address.getMain())
					customer.setMainAddress(address);
				else
					customer.getAdresses().add(address);
			}
			
			
			return customer;
		}else {
			
			return customer = null;
		}
	}

	@Override
	public Customer FindByCpf(String cpf) {
		Customer customer;
				
		if(ExistByCpf(cpf)) {
			handle.registerRowMapper(ConstructorMapper.factory(Customer.class));
			customer = handle.createQuery("SELECT * FROM customer WHERE cpf = ?")
					.bind(0, cpf).mapTo(Customer.class).one();
			
			handle.registerRowMapper(ConstructorMapper.factory(Address.class));
			List<Address> addresses = handle.createQuery("SELECT * FROM address WHERE customer_id = ?")
					.bind(0, customer.getId()).mapTo(Address.class).list();
			
			for (Address address : addresses) {
				if(address.getMain())
					customer.setMainAddress(address);
				else
					customer.getAdresses().add(address);
			}
			
			
			return customer;
		}else {
			
			return customer = null;
		}
	}
	
}
