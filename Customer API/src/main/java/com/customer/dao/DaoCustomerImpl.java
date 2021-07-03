package com.customer.dao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.locator.ClasspathSqlLocator;

import com.customer.dto.Address;
import com.customer.dto.Customer;
import com.customer.dto.Customer_InnerJoin_Address;
import com.customer.mapper.AddressMapper;
import com.customer.mapper.CustomerMapper;
import com.customer.mapper.Customer_InnerJoin_AddressMapper;
import com.customer.pojo.CustomerPojo;
import com.google.inject.Inject;

@SuppressWarnings("deprecation")
public class DaoCustomerImpl implements DaoCustomer{
	
	@Inject
	private Jdbi jdbi;
	
	@Override
	public Customer salvar(CustomerPojo customerPojo) {
		
		//============Setando a data/horario da criação do customer
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		customerPojo.setUpdateAt(LocalDateTime.now().format(formatter));
		customerPojo.setCreatedAt(customerPojo.getUpdateAt());
		
		//============INSERT do customer
		String sql1 = ClasspathSqlLocator.findSqlOnClasspath("com.customer.sql.insert-customer");
		Long idCustomer;
		try {
			idCustomer = jdbi.withHandle(handle ->
			handle.createUpdate(sql1)
			.bind("name", customerPojo.getName())
			.bind("email", customerPojo.getEmail())
			.bind("birthDate", customerPojo.getBirthDate())
			.bind("cpf", customerPojo.getCpf())
			.bind("gender", customerPojo.getGender())
			.bind("createdAt", customerPojo.getCreatedAt())
			.bind("updateAt", customerPojo.getUpdateAt())
			.executeAndReturnGeneratedKeys("id").mapTo(Long.class).one());
		}catch (Exception e) {
			return null;
		}
		customerPojo.setId(idCustomer);
		
		//============INSERT do address
		String sql2 = ClasspathSqlLocator.findSqlOnClasspath("com.customer.sql.insert-address");
		Long idAddress = jdbi.withHandle(handle ->
			handle.createUpdate(sql2)
			.bind("state", customerPojo.getAddress().getState())
			.bind("city", customerPojo.getAddress().getCity())
			.bind("neighborhood", customerPojo.getAddress().getNeighborhood())
			.bind("zipCode", customerPojo.getAddress().getZipCode())
			.bind("street", customerPojo.getAddress().getStreet())
			.bind("number", customerPojo.getAddress().getNumber())
			.bind("additionalInformation", customerPojo.getAddress().getAdditionalInformation())
			.bind("main", customerPojo.getAddress().getMain())
			.bind("customer_id", customerPojo.getId())
			.executeAndReturnGeneratedKeys("id").mapTo(Long.class).one());
		
		customerPojo.getAddress().setId(idAddress);
		
		//============Pegando customer e address criados no banco
		String sql3 = ClasspathSqlLocator.findSqlOnClasspath("com.customer.sql.select-customer");
		Customer customer = jdbi.withHandle(handle ->
				handle.createQuery(sql3)
				.bind("id", customerPojo.getId())
				.map(new CustomerMapper()).one());
		
		String sql4 = ClasspathSqlLocator.findSqlOnClasspath("com.customer.sql.select-address");
		Address address = jdbi.withHandle(handle ->
			handle.createQuery(sql4)
			.bind("id", customerPojo.getAddress().getId())
			.map(new AddressMapper()).one());
		
		if(address.getMain()) {
			customer.setMainAddress(address);
		}else {
			customer.getAdresses().add(address);
		}
		return customer;
	}

	@Override
	public List<Customer> listar() {
		String sql1 = ClasspathSqlLocator.findSqlOnClasspath("com.customer.sql.select-allCustomers");
		
		//==============Buscando customers
		List<Customer> customers = jdbi.withHandle(handle -> 
					handle.createQuery(sql1)
					.map(new CustomerMapper()).list());
		
		//==============Buscando os Adresses dos customers
		for (Customer customer : customers) {
			String sql2 = ClasspathSqlLocator.findSqlOnClasspath("com.customer.sql.select-commonCustomerAddress");
			
			List<Address> adresses = jdbi.withHandle(handle -> 
						handle.createQuery(sql2)
						.bind("customer_id", customer.getId())
						.map(new AddressMapper()).list());
			
			String sql3 = ClasspathSqlLocator.findSqlOnClasspath("com.customer.sql.select-customerMainAddress");
			
			Address mainAddress = null;
			try {
				mainAddress = jdbi.withHandle(handle -> 
						handle.createQuery(sql3)
						.bind("customer_id", customer.getId())
						.map(new AddressMapper()).one());
			}catch (Exception e) {
				if(e instanceof IllegalStateException){//Se não encontrar nenhum registro ele ignora o erro
				}else 
					e.printStackTrace();
			}
			
			customer.setMainAddress(mainAddress);
			customer.setAdresses(adresses);
		}
		return customers;
	}

	@Override
	public List<Customer> listarComFiltro(String sql) {
		
		//==========Pegando a ordem dos resultados da busca personalizada
		List<Customer_InnerJoin_Address> CIJAList = null;
		
		if((CIJAList = jdbi.withHandle(handle -> 
								   handle.createQuery(sql)
								   .map(new Customer_InnerJoin_AddressMapper())
								   .list()
										  )) == null) {
			return null;
		}
		
		//============================INICIO- Montando a lista de Customer===============================//
		Long lastCustomerId = 0L;
		int indexCustomer = -1;
		List<Customer> customers = new ArrayList<Customer>();
		String sqlCustomerTemp = ClasspathSqlLocator.findSqlOnClasspath("com.customer.sql.select-customer");
		String sqlAddressTemp = ClasspathSqlLocator.findSqlOnClasspath("com.customer.sql.select-address");
		
		for (Customer_InnerJoin_Address CIJA : CIJAList) {
			
			if(CIJA.getC_id() != lastCustomerId) {
				Customer customerTemp = jdbi.withHandle(handle -> 
					handle.createQuery(sqlCustomerTemp)
					.bind("id", CIJA.getC_id())
					.map(new CustomerMapper()).one());
				
				Address addressTemp = jdbi.withHandle(handle -> 
					handle.createQuery(sqlAddressTemp)
					.bind("id", CIJA.getA_id())
					.map(new AddressMapper()).one());
				
				if(addressTemp.getMain()) {
					customerTemp.setMainAddress(addressTemp);
				}else{
					customerTemp.getAdresses().add(addressTemp);
				}
				
				customers.add(customerTemp);
				lastCustomerId = CIJA.getC_id();
				indexCustomer++;
			}else {
				Address addressTemp = jdbi.withHandle(handle -> 
					handle.createQuery(sqlAddressTemp)
					.bind("id", CIJA.getA_id())
					.map(new AddressMapper()).one());
				
				if(addressTemp.getMain()) {
					customers.get(indexCustomer).setMainAddress(addressTemp);
				}else{
					customers.get(indexCustomer).getAdresses().add(addressTemp);
				}
			}
		}
		
		return customers;
	}
	
	@Override
	public Customer buscar(Long id) {
		//============Buscando Customer
		String sql1 = ClasspathSqlLocator.findSqlOnClasspath("com.customer.sql.select-customer");
		
		Customer customer = null;
		try {
			customer = jdbi.withHandle(handle ->
				handle.createQuery(sql1)
				.bind("id", id)
				.map(new CustomerMapper()).one());	
		}catch (Exception e) {
			if(e instanceof IllegalStateException)
				return null;
		}
		
		//============Buscando e setando Adresses
		String sql2 = ClasspathSqlLocator.findSqlOnClasspath("com.customer.sql.select-allCustomerAddresses");
		
		List<Address> adresses = jdbi.withHandle(handle ->
				handle.createQuery(sql2)
				.bind("customer_id", id)
				.map(new AddressMapper()).list());
		
		for (Address address : adresses) {
			if(address.getMain()) {
				customer.setMainAddress(address);
			}else {
				customer.getAdresses().add(address);
			}
		}
		
		return customer;
	}
	
	@Override
	public Customer atualizar(CustomerPojo customerPojo) {
		
		String sql = ClasspathSqlLocator.findSqlOnClasspath("com.customer.sql.select-IfCustomerExists");
		
		boolean existCustomer = jdbi.withHandle(handle ->	
				handle.createQuery(sql)
				.bind("id", customerPojo.getId()).mapTo(boolean.class).one());
		
		if(existCustomer) {
			if(customerPojo.getAddress() == null) {
				String sql1 = ClasspathSqlLocator.findSqlOnClasspath("com.customer.sql.update-customer");
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				
				customerPojo.setUpdateAt(LocalDateTime.now().format(formatter));
				
				jdbi.withHandle(handle ->
					handle.createUpdate(sql1)
					.bind("name", customerPojo.getName())
					.bind("email", customerPojo.getEmail())
					.bind("birthDate", customerPojo.getBirthDate())
					.bind("cpf", customerPojo.getCpf())
					.bind("gender", customerPojo.getGender())
					.bind("updateAt", customerPojo.getUpdateAt())
					.bind("id", customerPojo.getId())
					.execute());
						
				return buscar(customerPojo.getId());
				
			}else {
				if(customerPojo.getAddress().getMain()) {
					String sql2 = ClasspathSqlLocator.findSqlOnClasspath("com.customer.sql.select-IfCustomerMainAddressExists");
					
					boolean existMainAddress = jdbi.withHandle(handle ->
							handle.createQuery(sql2).bind("customer_id", customerPojo.getId()).mapTo(boolean.class).one());
					
					if(existMainAddress) {
						String sql3 = ClasspathSqlLocator.findSqlOnClasspath("com.customer.sql.update-mainAddress");
						
						jdbi.withHandle(handle ->
						handle.createUpdate(sql3)
						.bind("state", customerPojo.getAddress().getState())
						.bind("city", customerPojo.getAddress().getCity())
						.bind("neighborhood", customerPojo.getAddress().getNeighborhood())
						.bind("zipCode", customerPojo.getAddress().getZipCode())
						.bind("street", customerPojo.getAddress().getStreet())
						.bind("number", customerPojo.getAddress().getNumber())
						.bind("additionalInformation", customerPojo.getAddress().getAdditionalInformation())
						.bind("customer_id", customerPojo.getId())
						.bind("main", customerPojo.getAddress().getMain())
						.execute());
					}else {
						System.out.println("Customer não possui MainAddress para ser atualizado");
					}
					
				}
				return buscar(customerPojo.getId());
			}
		}else {
			return null;
		}
		
	}

	@Override
	public boolean deletar(Long id) {
		String sql = ClasspathSqlLocator.findSqlOnClasspath("com.customer.sql.select-IfCustomerExists");

		boolean existCustomer = jdbi.withHandle(handle ->	
			handle.createQuery(sql)
			.bind("id", id).mapTo(boolean.class).one());

		if(existCustomer) {
			//DELETA Adresses do Customer
			String sql1 = ClasspathSqlLocator.findSqlOnClasspath("com.customer.sql.delete-customerAddressByOnlyId");
			jdbi.withHandle(handle -> 
			handle.createUpdate(sql1)
				.bind("customer_id", id)
				.execute());

			//DELETA Customer
			String sql2 = ClasspathSqlLocator.findSqlOnClasspath("com.customer.sql.delete-customer");
			jdbi.withHandle(handle -> 
				handle.createUpdate(sql2)
				.bind("id", id)
				.execute());

			return true;
		}else {
			return false;
		}

	}
}
