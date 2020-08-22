package com.practice.demo.model;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Model for Customer
 * 
 * @author Anjitha
 *
 */
@Document(collection = "customer")
public class Customer implements Serializable {
	private static final long serialVersionUID = 8515246084941295161L;

	@Transient
	public static final String SEQUENCE_NAME = "customerSequence";

	@Id
	private Long customerNumber;
	@NotBlank
	private String customerName;
	private Integer age;
	private boolean status;

	public Long getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(Long customerNumber) {
		this.customerNumber = customerNumber;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
}
