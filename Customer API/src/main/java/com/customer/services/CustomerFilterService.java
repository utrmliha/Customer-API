package com.customer.services;

import com.customer.filter.CustomerFilter;

public interface CustomerFilterService {
	public String montarSqlComFiltro(CustomerFilter customerFilter);
}
