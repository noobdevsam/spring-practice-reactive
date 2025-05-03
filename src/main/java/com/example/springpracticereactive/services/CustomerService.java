package com.example.springpracticereactive.services;

import com.example.springpracticereactive.model.CustomerDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {
	
	Flux<CustomerDTO> listCustomers();
	
	Mono<CustomerDTO> getCustomerById(Integer id);
	
	Mono<CustomerDTO> saveNewCustomer(CustomerDTO customerDTO);
	
	Mono<CustomerDTO> updateCustomer(Integer id, CustomerDTO customerDTO);
	
	Mono<CustomerDTO> patchCustomer(Integer id, CustomerDTO customerDTO);
	
	Mono<Void> deleteCustomerById(Integer id);
}