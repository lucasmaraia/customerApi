package com.example.api.web.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.api.domain.Address;
import com.example.api.domain.Customer;
import com.example.api.service.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	private CustomerService service;

	public CustomerController(CustomerService service) {
		this.service = service;
	}

	@GetMapping
	public List<Customer> findAll(@RequestParam(defaultValue = "false") boolean paginated,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		if (paginated) {
			Pageable pageable = PageRequest.of(page, size);
			Page<Customer> customerPage = service.findAll(pageable);
			return customerPage.getContent();
		} else {
			return service.findAll();
		}
	}

	@GetMapping("/{id}")
	public Customer findById(@PathVariable Long id) {
		return service.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
	}

	@PostMapping
	public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
		service.saveCostomer(customer);
		return ResponseEntity.status(HttpStatus.CREATED).body(customer);
	}

	@PutMapping("{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
		Optional<Customer> optionalCustomer = service.findById(id);

		if (optionalCustomer.isPresent()) {
			Customer c = optionalCustomer.get();
			c.setName(customer.getName());
			c.setEmail(customer.getEmail());

			service.saveCostomer(c);
			return ResponseEntity.status(HttpStatus.OK).body(c);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
		Optional<Customer> optionalCustomer = service.findById(id);

		if (optionalCustomer.isPresent()) {
			Customer customer = optionalCustomer.get();
			service.deleteCostomer(customer);
			return ResponseEntity.status(HttpStatus.ACCEPTED).build();
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@PostMapping("{id}/addresses")
	public ResponseEntity<List<Address>> createAddresses(@PathVariable Long id, @RequestBody List<Address> addresses) {

		Customer customer = service.findById(id).orElse(null);
		if (customer == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}

		for (Address address : addresses) {
			address.setCustomer(customer);
		}

		customer.getAddresses().addAll(addresses);
		service.saveCostomer(customer);

		for (Address address : addresses) {
			address.setCustomer(null);
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(addresses);
	}

}
