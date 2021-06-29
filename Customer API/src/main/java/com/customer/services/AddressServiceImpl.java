package com.customer.services;

import java.util.ArrayList;
import java.util.List;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.reflect.ConstructorMapper;

import com.customer.dao.DaoAddress;
import com.customer.dto.Address;
import com.customer.filter.AddressFilter;
import com.customer.pojo.AddressPojo;
import com.customer.validation.AddressValidation;
import com.google.inject.Inject;

import spark.Request;
import spark.Response;

public class AddressServiceImpl implements AddressService{
	
	private Handle handle;
	@Inject 
	AddressValidation addressValidation;
	@Inject
	DaoAddress daoAddress;
	@Inject
	private Jdbi jdbi;
	
	@Override
	public Address Create(Request request, Response response) {
		//Address address = PojoToModelImpl.Address(addressPojo);
		Address address = null;
		
		try {
			address = addressValidation.validate(request);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		if(address != null) {
			address = daoAddress.save(address);
		}
		
		/*
		int customer_id = addressPojo.getCustomer_id();
		
		boolean existCustomer =	handle.createQuery("SELECT IF(EXISTS(SELECT id FROM customer WHERE id = ?), 1, 0)")
				.bind(0, customer_id).mapTo(boolean.class).one();
		
		if(existCustomer) {
			if(!ExistExactAddress(addressPojo)) {
				if(address.getMain()){
					//======================= INICIO Transforma o mainAddress como comum =======================//
					boolean existMainAddressCustomer =	handle.createQuery("SELECT IF(EXISTS(SELECT id FROM address WHERE customer_id = ? AND main = true), 1, 0)")
							.bind(0, customer_id).mapTo(boolean.class).one();
					
					if(existMainAddressCustomer) {
						handle.registerRowMapper(ConstructorMapper.factory(Address.class));
						Address addressTemp = handle.createQuery("SELECT * FROM address WHERE customer_id = "+customer_id+" AND main = true")
								.mapTo(Address.class).one();
						
						addressTemp.setMain(false);
						handle.createUpdate("UPDATE address SET state = ?, city = ?, neighborhood = ?, zipCode = ?, "
								+ "street = ?, number = ?, additionalInformation = ?, main = ? WHERE id = ?")
						.bind(0, addressTemp.getState())
						.bind(1, addressTemp.getCity())
						.bind(2, addressTemp.getNeighborhood())
						.bind(3, addressTemp.getZipCode())
						.bind(4, addressTemp.getStreet())
						.bind(5, addressTemp.getNumber())
						.bind(6, addressTemp.getAdditionalInformation())
						.bind(7, addressTemp.getMain())
						.bind(8, addressTemp.getId())
						.execute();
					}
					//========================= FIM Transforma o mainAddress como comum ========================//
					
					//============================== INICIO Insere novo mainAddress ============================//
					handle.createUpdate("INSERT INTO address "
							+ "(id, state, city, neighborhood, zipCode, street, number, additionalInformation, main, customer_id) VALUES "
							+ "(DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?)")
					.bind(0, address.getState())
					.bind(1, address.getCity())
					.bind(2, address.getNeighborhood())
					.bind(3, address.getZipCode())
					.bind(4, address.getStreet())
					.bind(5, address.getNumber())
					.bind(6, address.getAdditionalInformation())
					.bind(7, address.getMain())
					.bind(8, customer_id)
					.execute();
					
					return null;
					//================================ FIM Insere novo mainAddress =============================//
				}else {
					//================================ INICIO Insere novo Address ==============================//
					handle.createUpdate("INSERT INTO address "
							+ "(id, state, city, neighborhood, zipCode, street, number, additionalInformation, main, customer_id) VALUES "
							+ "(DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?)")
					.bind(0, address.getState())
					.bind(1, address.getCity())
					.bind(2, address.getNeighborhood())
					.bind(3, address.getZipCode())
					.bind(4, address.getStreet())
					.bind(5, address.getNumber())
					.bind(6, address.getAdditionalInformation())
					.bind(7, address.getMain())
					.bind(8, customer_id)
					.execute();
					
					return null;
					//================================== FIM Insere novo Address ===============================//
				}
			}else {
				ApiError apiError = new ApiError();
				apiError.setCode("create_address");
				apiError.setDescription("Address já existe!");
				return apiError;
			}
		}else {
			ApiError apiError = new ApiError();
			apiError.setCode("create_address");
			apiError.setDescription("Customer não existe!");
			return apiError;
		}*/
		return null;
	}
	
	@Override
	public ApiError Update(AddressPojo addressPojo) {
		//Address address = PojoToModelImpl.Address(addressPojo);
		Address address = new Address();
		
		Long customer_id = addressPojo.getCustomer_id();
		
		boolean exist =	handle.createQuery("SELECT IF(EXISTS(SELECT id FROM address WHERE id = ? AND customer_id = ?), 1, 0)")
				.bind(0, address.getId())
				.bind(1, customer_id).mapTo(boolean.class).one();
		/*
		if(exist) {
			if(address.getMain()) {
				handle.createUpdate("UPDATE address SET main = false WHERE customer_id = "+customer_id+" AND main = true;").execute();
				
				handle.createUpdate("UPDATE address SET state = ?, city = ?, neighborhood = ?, zipCode = ?, "
						+ "street = ?, number = ?, additionalInformation = ?, main = ? WHERE id = ? AND customer_id = ?")
				.bind(0, address.getState())
				.bind(1, address.getCity())
				.bind(2, address.getNeighborhood())
				.bind(3, address.getZipCode())
				.bind(4, address.getStreet())
				.bind(5, address.getNumber())
				.bind(6, address.getAdditionalInformation())
				.bind(7, address.getMain())
				.bind(8, address.getId())
				.bind(9, customer_id)
				.execute();
				
				return null;
			}else {
				
				handle.createUpdate("UPDATE address SET state = ?, city = ?, neighborhood = ?, zipCode = ?, "
						+ "street = ?, number = ?, additionalInformation = ?, main = ? WHERE id = ? AND customer_id = ?")
				.bind(0, address.getState())
				.bind(1, address.getCity())
				.bind(2, address.getNeighborhood())
				.bind(3, address.getZipCode())
				.bind(4, address.getStreet())
				.bind(5, address.getNumber())
				.bind(6, address.getAdditionalInformation())
				.bind(7, address.getMain())
				.bind(8, address.getId())
				.bind(9, customer_id)
				.execute();
				
				return null;
			}
		}else {
			ApiError apiError = new ApiError();
			apiError.setCode("update_address");
			apiError.setDescription("Enderço não encontrado");
			return apiError;
		}*/
		return null;
	}
	
	@Override
	public ApiError DeleteById(int id) {
		return null;
/*
		if(ExistById(id)) {
			handle.execute("DELETE FROM address WHERE id = "+id);
			return null;
		}else {
			ApiError apiError = new ApiError();
			apiError.setCode("delete_address");
			apiError.setDescription("Address não encontrado");
			return apiError;
		}*/
	}
	
	@Override
	public ApiError DeleteAddressCustomer(int idAddress, int idCustomer) {
		return null;
/*
		if(ExistCustomerAddress(idAddress, idCustomer)) {
			handle.execute("DELETE FROM address WHERE id = "+idAddress+" AND customer_id = "+idCustomer);
			return null;
		}else {
			ApiError apiError = new ApiError();
			apiError.setCode("delete_address");
			apiError.setDescription("Address não encontrado");
			return apiError;
		}*/
	}

	@Override
	public boolean ExistMainAddress(int customer_id) {
		return false;/*
		boolean exist = handle.createQuery("SELECT IF(EXISTS(SELECT id FROM address WHERE customer_id = ? AND main = 1), 1, 0)")
			.bind(0, customer_id)
			.mapTo(boolean.class)
			.one();
		return exist;*/
	}

	@Override
	public boolean ExistCustomerAddress(int idAddress, int idCustomer) {
		return false;/*
		boolean exist = handle.createQuery("SELECT IF(EXISTS(SELECT id FROM address WHERE customer_id = ? and id = ?), 1, 0)")
			.bind(0, idCustomer)
			.bind(1, idAddress)
			.mapTo(boolean.class)
			.one();
		return exist;*/
	}

	@Override
	public boolean ExistById(int id) {
		return false;/*
		boolean exist = handle.createQuery("SELECT IF(EXISTS(SELECT id FROM address WHERE id = ?), 1, 0)")
			.bind(0, id)
			.mapTo(boolean.class)
			.one();
		return exist;*/
	}

	@Override
	public boolean ExistExactAddress(AddressPojo addressPojo) {
		//Address address = PojoToModelImpl.Address(addressPojo);
		Address address = new Address();
		
		Long idCustomer = addressPojo.getCustomer_id();
		
		boolean exist = handle.createQuery("SELECT IF(EXISTS(SELECT id FROM address WHERE state = ? AND city = ? AND neighborhood = ? AND "
				+ "zipCode = ? AND street = ? AND number = ? AND additionalInformation = ? AND main = ? AND customer_id = ?), 1, 0)")
				.bind(0, address.getState())
				.bind(1, address.getCity())
				.bind(2, address.getNeighborhood())
				.bind(3, address.getZipCode())
				.bind(4, address.getStreet())
				.bind(5, address.getNumber())
				.bind(6, address.getAdditionalInformation())
				.bind(7, address.getMain())
				.bind(8, idCustomer)
				.mapTo(boolean.class)
				.first();
		return exist;
	}

	@Override
	public List<Address> FindAllWithFilter(AddressFilter addressFilter, int customer_id){
		
		//===============================INICIO- Personalizando a Query======================================//
		
		String Query = "SELECT * FROM address WHERE customer_id = "+customer_id+" AND ";
		List<String> filtrarPor = new ArrayList<String>();
		
		if(addressFilter.getId() == null || addressFilter.getId() < 1) {
		}else {
			filtrarPor.add("id = "+ addressFilter.getId());
		}
		if(addressFilter.getState() == null || addressFilter.getState().isBlank()) {
		}else {
			filtrarPor.add("state = "+ addressFilter.getState());
		}
		if(addressFilter.getCity() == null || addressFilter.getCity().isBlank()) {
		}else {
			filtrarPor.add("city LIKE '%"+ addressFilter.getCity()+ "%'");
		}
		if(addressFilter.getNeighborhood() == null || addressFilter.getNeighborhood().isBlank()) {
		}else {
			filtrarPor.add("neighborhood LIKE '%"+ addressFilter.getNeighborhood()+ "%'");
		}
		if(addressFilter.getZipCode() == null || addressFilter.getZipCode().isBlank()) {
		}else {
			filtrarPor.add("zipCode = "+ addressFilter.getZipCode());
		}
		if(addressFilter.getStreet() == null || addressFilter.getStreet().isBlank()) {
		}else {
			filtrarPor.add("street LIKE '%"+ addressFilter.getStreet()+"%'");
		}
		if(addressFilter.getNumber() == null || addressFilter.getNumber().isBlank()) {
		}else {
			filtrarPor.add("number = "+ addressFilter.getNumber());
		}
		if(addressFilter.getAdditionalInformation() == null || addressFilter.getAdditionalInformation().isBlank()) {
		}else {
			filtrarPor.add("additionalInformation LIKE '%"+ addressFilter.getAdditionalInformation()+ "%'");
		}
		if(addressFilter.isMain()) {
			filtrarPor.add("main = "+ addressFilter.isMain());
		}
		if(addressFilter.getSortBy() == null || addressFilter.getSortBy().isBlank()) {
			filtrarPor.add("ORDER BY id ");
		}
		else if(!(addressFilter.getSortBy() == null) || !addressFilter.getSortBy().isBlank()) {
			String sortBy = null;
			if(addressFilter.getSortBy().equalsIgnoreCase("ID"))
				sortBy = "id";
			else if(addressFilter.getSortBy().equalsIgnoreCase("STATE"))
				sortBy = "state";
			else if(addressFilter.getSortBy().equalsIgnoreCase("CITY"))
				sortBy = "city";
			else if(addressFilter.getSortBy().equalsIgnoreCase("NEIGHBORHOOD"))
				sortBy = "neighborhood";
			else if(addressFilter.getSortBy().equalsIgnoreCase("ZIPCODE"))
				sortBy = "zipCode";
			else if(addressFilter.getSortBy().equalsIgnoreCase("STREET"))
				sortBy = "street";
			else if(addressFilter.getSortBy().equalsIgnoreCase("NUMBER"))
				sortBy = "number";
			else if(addressFilter.getSortBy().equalsIgnoreCase("ADDITIONALINFORMATION"))
				sortBy = "additionalInformation";
			else if(addressFilter.getSortBy().equalsIgnoreCase("MAIN"))
				sortBy = "main";
			filtrarPor.add("ORDER BY "+ sortBy);
		}
		if(addressFilter.getSortOrder() == null || addressFilter.getSortOrder().isBlank()) {
			filtrarPor.add("ASC");
			
		}else{
			filtrarPor.add(addressFilter.getSortOrder());
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

		//======================FIM- Verificando a nescessidade de AND e WHERE===========================//
		
		
		List<Address> addresses;
		
		handle.registerRowMapper(ConstructorMapper.factory(Address.class));
		if((addresses = handle.createQuery(Query).mapTo(Address.class).list()) == null) {
			return null;
		}else {
			return addresses;
		}
	}

	@Override
	public List<Address> FindAllNoFilter(int customer_id){
		
		List<Address> adresses;
		String ResultExist = "SELECT IF(EXISTS(SELECT id FROM address WHERE customer_id = "+customer_id+"), 1, 0)"; 
		
		if(!handle.createQuery(ResultExist).mapTo(boolean.class).one())
			return adresses = new ArrayList<Address>();
		
		handle.registerRowMapper(ConstructorMapper.factory(Address.class));
		adresses = handle.createQuery("SELECT * FROM address WHERE customer_id = "+customer_id)
			.mapTo(Address.class).list();
		
		return adresses;
	}

	@Override
	public Address FindExactAddress(AddressPojo addressPojo) {
		//Address address = PojoToModelImpl.Address(addressPojo);
		Address address = new Address();
		
		Long idCustomer = addressPojo.getCustomer_id();
		
		if(ExistExactAddress(addressPojo)) {
			handle.registerRowMapper(ConstructorMapper.factory(Address.class));
			address = handle.createQuery("SELECT * FROM address WHERE state = ? AND city = ? AND neighborhood = ? AND "
					+ "zipCode = ? AND street = ? AND number = ? AND additionalInformation = ? AND main = ? AND customer_id = ?")
					.bind(0, address.getState())
					.bind(1, address.getCity())
					.bind(2, address.getNeighborhood())
					.bind(3, address.getZipCode())
					.bind(4, address.getStreet())
					.bind(5, address.getNumber())
					.bind(6, address.getAdditionalInformation())
					.bind(7, address.getMain())
					.bind(8, idCustomer)
					.mapTo(Address.class)
					.one();
			return address;
		}else {
			return address = null;
		}
	}

	@Override
	public Address FindAddressUser(int idAddress, int idCustomer) {
		
		boolean exist = handle.createQuery("SELECT IF(EXISTS(SELECT * FROM address WHERE customer_id = ? AND id = ?), 1, 0)")
				.bind(0, idCustomer)
				.bind(1, idAddress).mapTo(boolean.class).one();
		
		Address address;
		
		if(exist) {
			handle.registerRowMapper(ConstructorMapper.factory(Address.class));
			address = handle.createQuery("SELECT * FROM address WHERE customer_id = ? AND id = ?")
					.bind(0, idCustomer)
					.bind(1, idAddress).mapTo(Address.class).one();
			
			return address;
		}else {
			return address = null;
		}
	}
	
}
