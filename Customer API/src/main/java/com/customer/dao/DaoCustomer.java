package com.customer.dao;

import java.util.List;

import com.customer.dto.Customer;
import com.customer.filter.CustomerFilter;
import com.customer.pojo.CustomerPojo;

public interface DaoCustomer {
	public Customer salvar(CustomerPojo customerPojo);
	public List<Customer> listar();
	public List<Customer> listarComFiltro(String sql);
	public Customer buscar(Long id);
	public Customer atualizar(CustomerPojo customerPojo);
	public boolean deletar(Long id);
}
