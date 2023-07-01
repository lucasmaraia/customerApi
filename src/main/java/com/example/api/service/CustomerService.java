package com.example.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.api.domain.Customer;
import com.example.api.repository.CustomerRepository;

@Service
public class CustomerService {

	private CustomerRepository repository;

	public CustomerService(CustomerRepository repository) {
		this.repository = repository;
	}

	public List<Customer> findAll() {
		return repository.findAll();
	}

	public Optional<Customer> findById(Long id) {
		return repository.findById(id);
	}
	
	public void saveCostomer(Customer costumer) {
	    repository.save(costumer);
	    
	}
	
	public void deleteCostomer(Customer costumer) {
	    repository.delete(costumer);
	    
	}
	
	public Page<Customer> findAll(Pageable pageable) {
	    return repository.findAll(pageable);
	}


}
