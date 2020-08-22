package com.practice.demo.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import com.practice.demo.exception.DemoException;
import com.practice.demo.model.Customer;
import com.practice.demo.repository.CustomerRepository;

@RunWith(SpringRunner.class)
public class CustomerServiceTest {

	@InjectMocks
	private CustomerService customerService;

	@Mock
	private CustomerRepository customerRepository;

	@Mock
	private SequenceGeneratorService sequenceService;

	@Test
	public void addCustomerSuccessScenario() throws Exception {

		Customer mockCustomer = new Customer();
		mockCustomer.setCustomerName("Anjitha");
		mockCustomer.setAge(25);
		mockCustomer.setStatus(true);
		
		Customer mockCustomerRepositoryData = mockCustomer;
		mockCustomerRepositoryData.setCustomerNumber(1L);

		Mockito.when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(mockCustomerRepositoryData);
		Mockito.when(sequenceService.generateSequnce(Mockito.anyString())).thenReturn(1L);
		Customer customer = customerService.addCustomerDetails(mockCustomer);
		assertEquals(customer, mockCustomer);
	}

	@Test
	public void updateCustomerWithoutCustomerNumber() throws Exception {

		Customer mockCustomer = new Customer();
		mockCustomer.setCustomerName("Anjitha");
		mockCustomer.setAge(25);
		mockCustomer.setStatus(true);
		assertThrows(DemoException.class, () -> {
			customerService.updateCustomerDetails(mockCustomer);
		});
	}
	
	@Test
	public void updateCustomerWithInvalidCustomerNumber() throws Exception {

		Customer mockCustomer = new Customer();
		mockCustomer.setCustomerNumber(10L);
		mockCustomer.setCustomerName("Anjitha");
		mockCustomer.setAge(25);
		mockCustomer.setStatus(true);
		// existById method returns false
		Mockito.when(customerRepository.existsById(Mockito.anyLong())).thenReturn(false);
		assertThrows(DemoException.class, () -> {
			customerService.updateCustomerDetails(mockCustomer);
		});
	}
	
	
	@Test
	public void updateCustomerSuccess() throws Exception {

		Customer mockCustomer = new Customer();
		mockCustomer.setCustomerNumber(10L);
		mockCustomer.setCustomerName("Anjitha");
		mockCustomer.setAge(25);
		mockCustomer.setStatus(true);
		
		Mockito.when(customerRepository.existsById(Mockito.anyLong())).thenReturn(true);
		Mockito.when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(mockCustomer);
		Customer customer = customerService.updateCustomerDetails(mockCustomer);
		assertEquals(customer, mockCustomer);
	}

	@Test
	public void deleteCustomerWithInvalidCustomerNumber() throws Exception {
		// existById method returns false
		Mockito.when(customerRepository.existsById(Mockito.anyLong())).thenReturn(false);
		assertThrows(DemoException.class, () -> {
			customerService.deleteCustomerDetails(10L);
		});
	}
	
	@Test
	public void getCustomerWithInvalidCustomerNumber() throws Exception {
		// Data not present in repository
		Optional<Customer> mockData = Optional.empty();
		Mockito.when(customerRepository.findById(Mockito.anyLong())).thenReturn(mockData);
		
		assertThrows(DemoException.class, () -> {
			customerService.getCustomerDetails(10L);
		});
	}
	
	@Test
	public void getAllCustomerListIsEmpty() throws Exception {
		// Data not present in repository
		List<Customer> mockData = new ArrayList<>();
		Mockito.when(customerRepository.findAll()).thenReturn(mockData);
		
		assertThrows(DemoException.class, () -> {
			customerService.getAllCustomerDetails();
		});
	}
	
}
