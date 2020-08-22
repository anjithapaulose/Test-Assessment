package com.practice.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.practice.demo.model.Customer;

/**
 * Customer repository class
 * 
 * @author Anjitha
 *
 */
@Repository
public interface CustomerRepository extends MongoRepository<Customer, Long> {

}