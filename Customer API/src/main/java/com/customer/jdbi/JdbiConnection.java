package com.customer.jdbi;

import org.jdbi.v3.core.Jdbi;

public interface JdbiConnection {
	public Jdbi conectar(String url, String user, String pass);
}
