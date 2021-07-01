package com.customer.jdbi;

import org.jdbi.v3.core.Jdbi;

import com.google.inject.Singleton;

@Singleton
public class MySQLConnectionImpl implements JdbiConnection{
	
	@Override
	public Jdbi conectar(String url, String user, String pass) {
		Jdbi jdbi = Jdbi.create(url, user, pass);
		return jdbi;
	}
}
