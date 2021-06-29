package com.customer.dao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.locator.ClasspathSqlLocator;

import com.customer.dto.Address;
import com.customer.dto.Customer;
import com.customer.mapper.AddressMapper;
import com.customer.mapper.CustomerMapper;
import com.customer.pojo.CustomerPojo;
import com.google.inject.Inject;

public class DaoCustomerImpl implements DaoCustomer{
	
	@Inject
	private Jdbi jdbi;
	
	@Override
	public Customer save(CustomerPojo customerPojo) {
		
		//============Setando a data/horario da criação do customer
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		customerPojo.setUpdateAt(LocalDateTime.now().format(formatter));
		customerPojo.setCreatedAt(customerPojo.getUpdateAt());
		
		//============INSERT do customer
		String SQLInsertCustomer = ClasspathSqlLocator.findSqlOnClasspath("com.customer.sql.insert-customer");
		Long idCustomer;
		try {
			idCustomer = jdbi.withHandle(handle ->
			handle.createUpdate(SQLInsertCustomer)
			.bind("name", customerPojo.getName())
			.bind("email", customerPojo.getEmail())
			.bind("birthDate", customerPojo.getBirthDate())
			.bind("cpf", customerPojo.getCpf())
			.bind("gender", customerPojo.getGender())
			.bind("createdAt", customerPojo.getCreatedAt())
			.bind("updateAt", customerPojo.getUpdateAt())
			.executeAndReturnGeneratedKeys("id").mapTo(Long.class).one());
		}catch (Exception e) {
			// org.jdbi.v3.core.statement.UnableToExecuteStatementException: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry '915.555.340-70' for key 'cpf' 
			return null;
		}
		customerPojo.setId(idCustomer);
		
		//============INSERT do address
		String SQLInsertAddress = ClasspathSqlLocator.findSqlOnClasspath("com.customer.sql.insert-address");
		Long idAddress = jdbi.withHandle(handle ->
			handle.createUpdate(SQLInsertAddress)
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
		String SQLSelectCustomer = ClasspathSqlLocator.findSqlOnClasspath("com.customer.sql.select-customer");
		Customer customer = jdbi.withHandle(handle ->
				handle.createQuery(SQLSelectCustomer)
				.bind("id", customerPojo.getId())
				.map(new CustomerMapper()).one());
		
		String SQLSelectAddress = ClasspathSqlLocator.findSqlOnClasspath("com.customer.sql.select-address");
		Address address = jdbi.withHandle(handle ->
			handle.createQuery(SQLSelectAddress)
			.bind("id", customerPojo.getAddress().getId())
			.map(new AddressMapper()).one());
		
		if(address.getMain()) {
			customer.setMainAddress(address);
		}else {
			customer.getAdresses().add(address);
		}
		return customer;
	}
}
