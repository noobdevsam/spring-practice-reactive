package com.example.springpracticereactive.services.impl;

import com.example.springpracticereactive.domain.Customer;
import com.example.springpracticereactive.mappers.CustomerMapper;
import com.example.springpracticereactive.model.CustomerDTO;
import com.example.springpracticereactive.repositories.CustomerRepository;
import com.example.springpracticereactive.services.CustomerService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	private final CustomerRepository customerRepository;
	private final CustomerMapper customerMapper;
	
	public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
		this.customerRepository = customerRepository;
		this.customerMapper = customerMapper;
	}
	
	@Override
	public Flux<CustomerDTO> listCustomers() {
		return customerRepository.findAll()
			       .map(customerMapper::customerToCustomerDTO);
	}
	
	@Override
	public Mono<CustomerDTO> getCustomerById(Integer id) {
		return customerRepository.findById(id)
			       .map(customerMapper::customerToCustomerDTO);
	}
	
	@Override
	public Mono<CustomerDTO> saveNewCustomer(CustomerDTO customerDTO) {
		return customerRepository.save(customerMapper.customerDTOToCustomer(customerDTO))
			       .map(customerMapper::customerToCustomerDTO);
	}
	
	@Override
	public Mono<CustomerDTO> updateCustomer(Integer id, CustomerDTO customerDTO) {
		return customerRepository.findById(id).flatMap(
			foundCustomer -> {
				return customerRepository.save(
					new Customer(
						foundCustomer.id(),
						customerDTO.customerName(),
						foundCustomer.createdDate(),
						foundCustomer.lastModifiedDate()
					)
				);
			}).map(customerMapper::customerToCustomerDTO);
	}
	
	@Override
	public Mono<CustomerDTO> patchCustomer(Integer id, CustomerDTO customerDTO) {
		return customerRepository.findById(id)
			       .flatMap(foundCustomer -> {
				       return customerRepository.save(new Customer(
					       foundCustomer.id(),
					       customerDTO.customerName() != null ? customerDTO.customerName() : foundCustomer.customerName(),
					       foundCustomer.createdDate(),
					       foundCustomer.lastModifiedDate())
				       );
			       })
			       .map(customerMapper::customerToCustomerDTO);
	}
	
	@Override
	public Mono<Void> deleteCustomerById(Integer id) {
		return customerRepository.deleteById(id);
	}
}