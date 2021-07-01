package com.customer.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import com.customer.dto.Customer_InnerJoin_Address;

public class Customer_InnerJoin_AddressMapper implements RowMapper<Customer_InnerJoin_Address>{

	@Override
	public Customer_InnerJoin_Address map(ResultSet rs, StatementContext ctx) throws SQLException {
		Customer_InnerJoin_Address CIJAMapper = new Customer_InnerJoin_Address();
		CIJAMapper.setA_id(rs.getLong("a_id"));
		CIJAMapper.setC_id(rs.getLong("c_id"));
		return CIJAMapper;
	}

}
