package com.customer.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import com.customer.dto.Customer;

public class CustomerMapper implements RowMapper<Customer>{

	@Override
	public Customer map(ResultSet rs, StatementContext ctx) throws SQLException {
		Customer customer = new Customer();
		customer.setId(rs.getLong("id"));
		customer.setUuid(rs.getString("uuid"));
		customer.setName(rs.getString("name"));
		customer.setEmail(rs.getString("email"));
		customer.setBirthDate(rs.getString("birthDate"));
		customer.setCpf(rs.getString("cpf"));
		customer.setGender(rs.getString("gender"));
		customer.setCreatedAt(rs.getString("createdAt"));
		customer.setUpdateAt(rs.getString("updateAt"));
		return customer;
	}

	
}
