package com.customer.interfaces;

import java.util.List;

import com.customer.dto.Address;
import com.customer.dto.ApiError;
import com.customer.filter.AddressFilter;
import com.customer.pojo.AddressPojo;

public interface IDaoAddress {
	
	public ApiError Create(AddressPojo addressPojo);
	
	public ApiError Update(AddressPojo addressPojo);
	
	public ApiError DeleteById(int id);
	
	public ApiError DeleteAddressCustomer(int idAddress, int idCustomer);

	public boolean ExistMainAddress(int customer_id);
	
	public boolean ExistCustomerAddress(int idAddress, int idCustomer);
	
	public boolean ExistById(int id);
	
	public boolean ExistExactAddress(AddressPojo addressPojo);
	
	public List<Address> FindAllWithFilter(AddressFilter addressFilter, int customer_id);
	
	public List<Address> FindAllNoFilter(int customer_id);
	
	public Address FindExactAddress(AddressPojo addressPojo);
	
	public Address FindAddressUser(int idAddress, int idCustomer);
}
