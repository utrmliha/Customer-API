package com.customer.jdbi;

import org.jdbi.v3.core.Jdbi;

public interface JdbiConnection {
	public void conectar();
	public Jdbi getJdbi();
}
