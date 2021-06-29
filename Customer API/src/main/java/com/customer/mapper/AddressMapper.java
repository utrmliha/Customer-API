package com.customer.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import com.customer.dto.Address;

public class AddressMapper implements RowMapper<Address>{

	@Override
	public Address map(ResultSet rs, StatementContext ctx) throws SQLException {
		Address address = new Address();
		address.setId(Long.parseLong(rs.getString("id")));
		address.setState(rs.getString("state"));
		address.setCity(rs.getString("city"));
		address.setNeighborhood(rs.getString("neighborhood"));
		address.setZipCode(rs.getString("zipCode"));
		address.setStreet(rs.getString("street"));
		address.setNumber(rs.getString("number"));
		address.setAdditionalInformation(rs.getString("additionalInformation"));
		address.setMain(rs.getBoolean("main"));
		return address;
	}

}
