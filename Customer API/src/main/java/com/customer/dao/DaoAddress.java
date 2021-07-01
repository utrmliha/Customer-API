package com.customer.dao;

import java.util.List;

import com.customer.dto.Address;
import com.customer.pojo.AddressPojo;

public interface DaoAddress {
	public Address salvar(AddressPojo addressPojo);
	public List<Address> listar(Long customer_id);
	public List<Address> listarComFiltro(String sql, Long customer_id);
	public Address buscar(AddressPojo addressPojo);
	public Address atualizar(AddressPojo addressPojo);
	public boolean deletar(AddressPojo addressPojo);
}
