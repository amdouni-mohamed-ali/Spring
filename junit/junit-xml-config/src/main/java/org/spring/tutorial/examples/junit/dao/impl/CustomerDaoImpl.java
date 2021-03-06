package org.spring.tutorial.examples.junit.dao.impl;

import org.spring.tutorial.examples.junit.dao.CustomerDao;
import org.spring.tutorial.examples.junit.entities.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {

	private List<Customer> listCustomers;
	
	public CustomerDaoImpl() {
		listCustomers = new ArrayList<>();
	}
	
	public Customer findById(Long id) {
		
		for(Customer c : listCustomers){
			if(Long.compare(c.getIdentifier(), id)==0){
				return c;
			}
		}
		return null;
	}

	public void init() {
		
		listCustomers.add(new Customer(1L, "customer1", "customer1"));
		listCustomers.add(new Customer(2L, "customer2", "customer2"));
		listCustomers.add(new Customer(3L, "customer3", "customer3"));
	}

}
