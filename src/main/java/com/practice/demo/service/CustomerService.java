package com.practice.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.practice.demo.exception.DemoException;
import com.practice.demo.model.Customer;
import com.practice.demo.repository.CustomerRepository;

/**
 * Service for customer crud operations
 * 
 * @author Anjitha
 *
 */
@Service
public class CustomerService {
	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	SequenceGeneratorService sequenceGenerator;

	/**
	 * Method for adding customer details
	 * 
	 * @param customer
	 *            Customer instance
	 * @return Added customer details
	 */
	public Customer addCustomerDetails(Customer customer) {
		customer.setCustomerNumber(sequenceGenerator.generateSequnce(Customer.SEQUENCE_NAME));
		Customer details = customerRepository.save(customer);
		return details;
	}

	/**
	 * Method for updating customer details
	 * 
	 * @param customer
	 *            Customer instance
	 * @return Updated customer details
	 */
	@CachePut(value = "customer", key = "#customer.customerNumber")
	public Customer updateCustomerDetails(Customer customer) {
		if (customer.getCustomerNumber() == null) {
			throw new DemoException(HttpStatus.BAD_REQUEST, "CustomerNumber is mandatory");
		} else if (!customerRepository.existsById(customer.getCustomerNumber())) {
			throw new DemoException(HttpStatus.NOT_FOUND, "Customer account does not exist");
		}

		Customer details = customerRepository.save(customer);
		return details;
	}

	/**
	 * Method for deleting customer details
	 * 
	 * @param customerNumber
	 *            Customer Number
	 */
	@CacheEvict(value = "customer", key = "#customerNumber")
	public void deleteCustomerDetails(Long customerNumber) {
		if (!customerRepository.existsById(customerNumber)) {
			throw new DemoException(HttpStatus.NOT_FOUND, "Customer account does not exist");
		}
		customerRepository.deleteById(customerNumber);
	}

	/**
	 * Method for getting customer details of a given customer
	 * 
	 * @param customerNumber
	 *            Customer Number
	 * @return Customer details
	 */
	@Cacheable(value = "customer", key = "#customerNumber")
	public Customer getCustomerDetails(Long customerNumber) {
		Optional<Customer> customerData = customerRepository.findById(customerNumber);
		if (!customerData.isPresent()) {
			throw new DemoException(HttpStatus.NOT_FOUND, "Customer account does not exist");
		}

		Customer customerDetails = customerData.get();
		// Throw 401 if status is false
		if (!customerDetails.isStatus()) {
			throw new DemoException(HttpStatus.UNAUTHORIZED, "Status is false");
		}
		return customerDetails;
	}

	/**
	 * Method for getting all customer details
	 * 
	 * @return List<Customer>
	 */
	public List<Customer> getAllCustomerDetails() {
		List<Customer> customers = customerRepository.findAll();
		if (customers.isEmpty()) {
			throw new DemoException(HttpStatus.NOT_FOUND, "No data found");
		}
		return customers;
	}
}
