package com.customer.services;

import java.util.List;

import com.customer.dao.DaoAddress;
import com.customer.dto.Address;
import com.customer.error.MappingException;
import com.customer.filter.AddressFilter;
import com.customer.pojo.AddressPojo;
import com.customer.validation.AddressFilterValidation;
import com.customer.validation.AddressValidation;
import com.google.inject.Inject;

import spark.Request;
import spark.Response;

public class AddressServiceImpl implements AddressService{
	
	@Inject 
	private AddressValidation addressValidation;
	@Inject
	private AddressFilterValidation addressFilterValidation;
	@Inject
	private AddressFilterService addressFilterService;
	@Inject
	private DaoAddress daoAddress;
	
	@Override
	public Address criar(Request request, Response response) throws MappingException {
		AddressPojo addressPojo = null;
		
		addressPojo = addressValidation.validate(request);
		addressPojo.setCustomer_id(Long.parseLong(request.params("id")));
		
		Address address;		
		if((address = daoAddress.salvar(addressPojo)) == null) {
			throw new MappingException("Customer não encontrado.");
		}else {
			response.type("application/json");
			return address;
		}
	}

	@Override
	public List<Address> listar(Request request, Response response) throws MappingException {
		List<Address> adresses = null;
		AddressFilter addressFilter = null;
		Long customer_id = Long.parseLong(request.params("id"));
		
		if((addressFilter = addressFilterValidation.validate(request)) == null) {
			adresses = daoAddress.listar(customer_id);
		}else {
			String sql = addressFilterService.montarSqlComFiltro(addressFilter);
			adresses = daoAddress.listarComFiltro(sql, customer_id);
		}
		
		if(adresses != null && adresses.size() > 0) {
			response.type("application/json");
			return adresses;
		}else {
			throw new MappingException("Adresses não encontrado.");
		}
		
	}

	@Override
	public Address buscar(Request request, Response response) throws MappingException {
		AddressPojo addressPojo = new AddressPojo();
		addressPojo.setId(Long.parseLong(request.params("address_id")));
		addressPojo.setCustomer_id(Long.parseLong(request.params("id")));
		
		Address address = daoAddress.buscar(addressPojo);
		if(address != null) {
			response.type("application/json");
			return address;
		}else {
			throw new MappingException("Address não encontrado.");
		}
	}

	@Override
	public Address atualizar(Request request, Response response) throws MappingException {
		AddressPojo addressPojo = null;
		
		addressPojo = addressValidation.validate(request);
		addressPojo.setCustomer_id(Long.parseLong(request.params("id")));
		addressPojo.setId(Long.parseLong(request.params("address_id")));
		
		Address address;
		if((address = daoAddress.atualizar(addressPojo)) != null){
			response.type("application/json");
			return address;
		}else {
			throw new MappingException("Address não encontrado.");
		}
	}

	@Override
	public Address deletar(Request request, Response response) throws MappingException {
		AddressPojo addressPojo = new AddressPojo();
		addressPojo.setId(Long.parseLong(request.params("address_id")));
		addressPojo.setCustomer_id(Long.parseLong(request.params("id")));
		
		if(daoAddress.deletar(addressPojo)) {
			throw new MappingException("Address deletado com sucesso!");
		}else {
			throw new MappingException("Address não encontrado.");
		}
	}
	
}
