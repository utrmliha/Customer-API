package com.customer.jdbi;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

import com.google.inject.Singleton;

@Singleton
public class JdbiConnection {
	private static String url = "jdbc:mysql://localhost:3306/mydbcustomers";
	private static String user = "root";
	private static String pass = "admin";
	private static Jdbi jdbi = null;
	private static Handle handle = null;
	
	static {
		conectar();
	}
	
	public JdbiConnection() {
		conectar();
	}
	
	public static void conectar() {
		jdbi = Jdbi.create(url, user, pass);
	}
	
	public static Handle getHandle() {
		handle = jdbi.open();
		return handle;
	}
}
