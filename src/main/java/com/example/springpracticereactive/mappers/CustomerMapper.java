package com.example.springpracticereactive.mappers;

import com.example.springpracticereactive.domain.Customer;
import com.example.springpracticereactive.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
	
	Customer customerDTOToCustomer(CustomerDTO customerDTO);
	
	CustomerDTO customerToCustomerDTO(Customer customer);
}