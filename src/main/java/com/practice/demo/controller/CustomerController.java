package com.practice.demo.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.practice.demo.model.Customer;
import com.practice.demo.service.CustomerService;

/**
 * Controller class for customer crud operations
 * 
 * @author Anjitha
 *
 */
@RestController
@RequestMapping(value = "/customer")
public class CustomerController {

	@Autowired
	CustomerService customerService;

	/**
	 * Endpoint for adding a new customer
	 * 
	 * @param customer
	 *            Customer
	 * @return Success(Customer object)/Failure message
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<?> addCustomerDetails(@RequestBody @Valid Customer customer) {
		Customer details = customerService.addCustomerDetails(customer);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{customerNumber}")
				.buildAndExpand(details.getCustomerNumber()).toUri();
		return ResponseEntity.created(location).body(details);
	}

	/**
	 * Endpoint for updating customer details
	 * 
	 * @param customer
	 *            Customer instance
	 * @return Success(Customer object)/Failure message
	 */
	@RequestMapping(value = "/", method = RequestMethod.PUT)
	public ResponseEntity<?> updateCustomerDetails(@RequestBody @Valid Customer customer) {
		Customer details = customerService.updateCustomerDetails(customer);
		return ResponseEntity.ok(details);
	}

	/**
	 * Endpoint for deleting a customer details
	 * 
	 * @param customerNumber
	 *            Customer number
	 * @return 204 response if success or failure message
	 */
	@RequestMapping(path = "/{customerNumber}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteCustomerDetails(@PathVariable Long customerNumber) {
		customerService.deleteCustomerDetails(customerNumber);
		return ResponseEntity.noContent().build();
	}

	/**
	 * Endpoint for getting details corresponding to a customer number
	 * 
	 * @param customerNumber
	 *            Customer number
	 * @return Success(Customer object)/Failure message
	 */
	@RequestMapping(path = "/{customerNumber}", method = RequestMethod.GET)
	public ResponseEntity<?> getCustomerDetails(@PathVariable Long customerNumber) {
		Customer customerDetails = customerService.getCustomerDetails(customerNumber);
		return ResponseEntity.ok(customerDetails);
	}

	/**
	 * Endpoint for getting all customer details
	 * 
	 * @return List of customer details
	 */
	@RequestMapping(path = "/", method = RequestMethod.GET)
	public ResponseEntity<?> getAllCustomerDetails() {
		List<Customer> details = customerService.getAllCustomerDetails();
		return ResponseEntity.ok(details);
	}

	/**
	 * Endpoint for testing
	 * 
	 * @return Returns Customer Service api
	 */
	@RequestMapping(path = "/health", method = RequestMethod.GET)
	public ResponseEntity<?> healthCheck() {
		return ResponseEntity.ok("Customer Service api");
	}
}
