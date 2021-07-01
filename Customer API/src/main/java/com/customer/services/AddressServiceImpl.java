package com.customer.services;

import java.util.List;

import com.customer.dao.DaoAddress;
import com.customer.dto.Address;
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
	public Address criar(Request request, Response response) {
		AddressPojo addressPojo = null;
		
		try {
			addressPojo = addressValidation.validate(request);
			addressPojo.setCustomer_id(Long.parseLong(request.params("id")));
		} catch (Exception e) {
			e.printStackTrace();
			return null; //ERRO Json inválido
		}
		
		Address address;		
		if((address = daoAddress.salvar(addressPojo)) == null) {
			return null;//Customer não existe
		}else {
			return address;
		}
	}

	@Override
	public List<Address> listar(Request request, Response response) {
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
			return adresses;
		}else {
			return null;//RETORNA ERROR ADRESSES NAO ENCONTRADOS
		}
		
	}

	@Override
	public Address buscar(Request request, Response response) {
		AddressPojo addressPojo = new AddressPojo();
		addressPojo.setId(Long.parseLong(request.params("address_id")));
		addressPojo.setCustomer_id(Long.parseLong(request.params("id")));
		
		Address address = daoAddress.buscar(addressPojo);
		if(address != null) {
			return address;
		}else {
			return null;//RETORNA ERROR ADDRESS NAO ENCONTRADO
		}
	}

	@Override
	public Address atualizar(Request request, Response response) {
		AddressPojo addressPojo = null;
		
		try {
			addressPojo = addressValidation.validate(request);
			addressPojo.setCustomer_id(Long.parseLong(request.params("id")));
			addressPojo.setId(Long.parseLong(request.params("address_id")));
		} catch (Exception e) {
			e.printStackTrace();
			return null; //ERRO Json inválido
		}
		
		Address address;
		if((address = daoAddress.atualizar(addressPojo)) != null) {
			return address;
		}else {
			return null;//RETORNA ERROR ADDRESS NAO ENCONTRADO
		}
	}

	@Override
	public Address deletar(Request request, Response response) {
		AddressPojo addressPojo = new AddressPojo();
		addressPojo.setId(Long.parseLong(request.params("address_id")));
		addressPojo.setCustomer_id(Long.parseLong(request.params("id")));
		
		if(daoAddress.deletar(addressPojo)) {
			return null;//RETORNA SUCESSO
		}else {
			return null;//RETORNA ERROR ADDRESS NAO ENCONTRADO
		}
	}
	
}
