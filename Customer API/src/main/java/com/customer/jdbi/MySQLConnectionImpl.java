package com.customer.jdbi;

import org.jdbi.v3.core.Jdbi;

import com.google.inject.Singleton;

@Singleton
public class MySQLConnectionImpl implements JdbiConnection{
	private static String url = "jdbc:mysql://localhost:3306/mydbcustomers";
	private static String user = "root";
	private static String pass = "admin";
	private static Jdbi jdbi = null;
		
	public MySQLConnectionImpl() {
		conectar();
	}
	
	@Override
	public void conectar() {
		jdbi = Jdbi.create(url, user, pass);
	}

	@Override
	public Jdbi getJdbi() {
		return jdbi;
	}

}
