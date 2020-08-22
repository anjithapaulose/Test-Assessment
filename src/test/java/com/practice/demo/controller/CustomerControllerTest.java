package com.practice.demo.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.demo.model.Customer;
import com.practice.demo.service.CustomerService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CustomerController.class)
public class CustomerControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CustomerService customerService;

	@Test
	public void addCustomer() throws Exception {

		Customer mockCustomer = new Customer();
		mockCustomer.setCustomerNumber(1L);
		mockCustomer.setCustomerName("Anjitha");
		mockCustomer.setAge(25);
		mockCustomer.setStatus(true);

		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(mockCustomer);

		Mockito.when(customerService.addCustomerDetails(Mockito.any(Customer.class))).thenReturn(mockCustomer);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/customer/")
				.header("Authorization", "Basic dGVjaG5pY2FsOkFzc2Vzc21lbnQ=")
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonInString);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String content = response.getContentAsString();
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		assertEquals("http://localhost/customer/1", response.getHeader(HttpHeaders.LOCATION));
		assertEquals(content, jsonInString);
	}

	@Test
	public void updateCustomer() throws Exception {

		Customer mockCustomer = new Customer();
		mockCustomer.setCustomerNumber(1L);
		mockCustomer.setCustomerName("Anjitha");
		mockCustomer.setAge(25);
		mockCustomer.setStatus(true);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(mockCustomer);
		Mockito.when(customerService.updateCustomerDetails(Mockito.any(Customer.class))).thenReturn(mockCustomer);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/customer/")
				.header("Authorization", "Basic dGVjaG5pY2FsOkFzc2Vzc21lbnQ=")
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonInString);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String content = response.getContentAsString();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(content, jsonInString);
	}
	
	
	@Test
	public void deleteCustomer() throws Exception {

		Mockito.doNothing().when(customerService).deleteCustomerDetails(5L);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/customer/5")
				.header("Authorization", "Basic dGVjaG5pY2FsOkFzc2Vzc21lbnQ=");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
	}
	
	@Test
	public void getCustomer() throws Exception {

		Customer mockCustomer = new Customer();
		mockCustomer.setCustomerNumber(1L);
		mockCustomer.setCustomerName("Anjitha");
		mockCustomer.setAge(25);
		mockCustomer.setStatus(true);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(mockCustomer);
		Mockito.when(customerService.getCustomerDetails(Mockito.anyLong())).thenReturn(mockCustomer);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/customer/1")
				.header("Authorization", "Basic dGVjaG5pY2FsOkFzc2Vzc21lbnQ=");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String content = response.getContentAsString();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(content, jsonInString);
	}
	
	@Test
	public void getAllCustomer() throws Exception {
		List<Customer> customerList = new ArrayList<>();
		
		Customer mockCustomer1 = new Customer();
		mockCustomer1.setCustomerNumber(1L);
		mockCustomer1.setCustomerName("Anjitha");
		mockCustomer1.setAge(25);
		mockCustomer1.setStatus(true);
		
		Customer mockCustomer2 = new Customer();
		mockCustomer2.setCustomerNumber(1L);
		mockCustomer2.setCustomerName("Anjitha");
		mockCustomer2.setAge(25);
		mockCustomer2.setStatus(true);
		
		customerList.add(mockCustomer1);
		customerList.add(mockCustomer2);
		
		ObjectMapper mapper = new ObjectMapper();
		String customerListString = mapper.writeValueAsString(customerList);
		
		Mockito.when(customerService.getAllCustomerDetails()).thenReturn(customerList);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/customer/")
				.header("Authorization", "Basic dGVjaG5pY2FsOkFzc2Vzc21lbnQ=");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String content = response.getContentAsString();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(content, customerListString);
	}
	
	@Test
	public void authorizationTestWithoutProvidingCredentials() throws Exception {
		Mockito.doNothing().when(customerService).deleteCustomerDetails(5L);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/customer/5");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatus());
	}

	
}
