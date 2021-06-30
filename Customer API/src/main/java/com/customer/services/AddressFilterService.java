package com.customer.services;

import com.customer.filter.AddressFilter;

public interface AddressFilterService {
	public String montarSqlComFiltro(AddressFilter addressFilter);
}
