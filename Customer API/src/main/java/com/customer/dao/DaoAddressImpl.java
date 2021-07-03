package com.customer.dao;

import java.util.List;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.locator.ClasspathSqlLocator;

import com.customer.dto.Address;
import com.customer.mapper.AddressMapper;
import com.customer.pojo.AddressPojo;
import com.google.inject.Inject;

@SuppressWarnings("deprecation")
public class DaoAddressImpl implements DaoAddress{

	@Inject
	Jdbi jdbi;
	
	@Override
	public Address salvar(AddressPojo addressPojo) {
		String sql1 = ClasspathSqlLocator.findSqlOnClasspath("com.customer.sql.select-IfCustomerExists");
		
		boolean existsCustomer = jdbi.withHandle(handle ->
				handle.createQuery(sql1)
				.bind("id", addressPojo.getCustomer_id())
				.mapTo(boolean.class).one());
		
		if(existsCustomer) {
			if(addressPojo.getMain()) {
				String sql2 = ClasspathSqlLocator.findSqlOnClasspath("com.customer.sql.select-IfCustomerMainAddressExists");

				boolean existsCustomerMainAddress = jdbi.withHandle(handle ->
					handle.createQuery(sql2).mapTo(boolean.class).one());

				if(existsCustomerMainAddress) {
					//======================Transforma o mainAddress como comum
					String sql3 = ClasspathSqlLocator.findSqlOnClasspath("com.customer.sql.update-mainAddressToCommonAddress");

					jdbi.withHandle(handle ->
						handle.createUpdate(sql3).bind("customer_id", addressPojo.getCustomer_id()).execute());
				}
			}
			//======================Adiciona novo Address
			String sql4 = ClasspathSqlLocator.findSqlOnClasspath("com.customer.sql.insert-address");

			Long idAddress = jdbi.withHandle(handle ->
				handle.createUpdate(sql4)
				.bind("state", addressPojo.getState())
				.bind("city", addressPojo.getCity())
				.bind("neighborhood", addressPojo.getNeighborhood())
				.bind("zipCode", addressPojo.getZipCode())
				.bind("street", addressPojo.getStreet())
				.bind("number", addressPojo.getNumber())
				.bind("additionalInformation", addressPojo.getAdditionalInformation())
				.bind("main", addressPojo.getMain())
				.bind("customer_id", addressPojo.getCustomer_id())
				.executeAndReturnGeneratedKeys("id").mapTo(Long.class).one());
			
			addressPojo.setId(idAddress);
			
			return buscar(addressPojo);
		}else {
			return null;
		}
	}

	@Override
	public List<Address> listar(Long customer_id) {
		String sql = ClasspathSqlLocator.findSqlOnClasspath("com.customer.sql.select-allCustomerAddresses");
		List<Address> adresses = jdbi.withHandle(handle -> 
				handle.createQuery(sql)
				.bind("customer_id", customer_id)
				.map(new AddressMapper()).list());
		return adresses;
	}

	@Override
	public List<Address> listarComFiltro(String sql, Long customer_id) {
		List<Address> adresses;
		
		if((adresses = 
				jdbi.withHandle(handle -> handle.createQuery(sql)
						.bind("customer_id", customer_id)
						.map(new AddressMapper()).list())
				) == null) {
			return null;
		}else {
			return adresses;
		}
	}

	@Override
	public Address buscar(AddressPojo addressPojo) {
		String sql = ClasspathSqlLocator.findSqlOnClasspath("com.customer.sql.select-customerAddress");
		Address address = null;
		try {
			address = jdbi.withHandle(handle -> 
				handle.createQuery(sql)
				.bind("id", addressPojo.getId())
				.bind("customer_id", addressPojo.getCustomer_id())
				.map(new AddressMapper()).one());
		}catch (Exception e) {
			if(e instanceof IllegalStateException)
				return null;
		}
		return address;
	}

	@Override
	public Address atualizar(AddressPojo addressPojo) {
		String sql1 = ClasspathSqlLocator.findSqlOnClasspath("com.customer.sql.select-IfCustomerAddressExists");
		boolean addressExists = jdbi.withHandle(handle ->
				handle.createQuery(sql1)
				.bind("id", addressPojo.getId())
				.bind("customer_id", addressPojo.getCustomer_id()).mapTo(boolean.class).one());
		if(addressExists) {
			//===INICIO=== Obtem o main desse address no banco
			String sql2 = ClasspathSqlLocator.findSqlOnClasspath("com.customer.sql.select-mainOfAddress");
			
			boolean getMain = jdbi.withHandle(handle ->
					handle.createQuery(sql2)
					.bind("id", addressPojo.getId())
					.bind("customer_id", addressPojo.getCustomer_id())
					.mapTo(boolean.class).one());
			//===FIM=== Obtem o main desse address no banco
			
			/*Se o address passado para atualização for main e o do banco não
			 *então ele muda o mainAddress do customer pra false para colocar esse como mainAddress  */
			 
			if(addressPojo.getMain() && !getMain) {
				String sql3 = ClasspathSqlLocator.findSqlOnClasspath("com.customer.sql.update-mainAddressToCommonAddress");
				jdbi.withHandle(handle ->
						handle.createUpdate(sql3)
						.bind("customer_id", addressPojo.getCustomer_id()).execute());
			}
			String sql4 = ClasspathSqlLocator.findSqlOnClasspath("com.customer.sql.update-customerAddress");
			
			jdbi.withHandle(handle ->
				handle.createUpdate(sql4)
				.bind("state", addressPojo.getState())
				.bind("city", addressPojo.getCity())
				.bind("neighborhood", addressPojo.getNeighborhood())
				.bind("zipCode", addressPojo.getZipCode())
				.bind("street", addressPojo.getStreet())
				.bind("number", addressPojo.getNumber())
				.bind("additionalInformation", addressPojo.getAdditionalInformation())
				.bind("main", addressPojo.getMain())
				.bind("id", addressPojo.getId())
				.bind("customer_id", addressPojo.getCustomer_id())
				.execute());
			
			return buscar(addressPojo);
			
		}else {
			return null;
		}
	}

	@Override
	public boolean deletar(AddressPojo addressPojo) {
		String sql1 = ClasspathSqlLocator.findSqlOnClasspath("com.customer.sql.select-IfCustomerAddressExists");
		boolean addressExists = jdbi.withHandle(handle ->
				handle.createQuery(sql1)
				.bind("id", addressPojo.getId())
				.bind("customer_id", addressPojo.getCustomer_id()).mapTo(boolean.class).one());
		if(addressExists) {
			String sql2 = ClasspathSqlLocator.findSqlOnClasspath("com.customer.sql.delete-customerAddress");
			jdbi.withHandle(handle -> 
			handle.createUpdate(sql2)
			.bind("id", addressPojo.getId())
			.bind("customer_id", addressPojo.getCustomer_id())
			.execute());
			return true;
		}else {
			return false;
		}
	}
}
