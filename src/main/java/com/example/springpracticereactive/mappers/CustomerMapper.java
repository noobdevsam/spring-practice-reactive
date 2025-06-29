package com.example.springpracticereactive.mappers;

import com.example.springpracticereactive.domain.Customer;
import com.example.springpracticereactive.model.CustomerDTO;
import org.mapstruct.Mapper;

/**
 * Mapper interface for converting between Customer and CustomerDTO objects.
 * Utilizes MapStruct for automatic mapping implementation.
 */
@Mapper(componentModel = "spring")
public interface CustomerMapper {

    /**
     * Converts a CustomerDTO object to a Customer entity.
     *
     * @param customerDTO The CustomerDTO object to be converted.
     * @return The corresponding Customer entity.
     */
    Customer customerDTOToCustomer(CustomerDTO customerDTO);

    /**
     * Converts a Customer entity to a CustomerDTO object.
     *
     * @param customer The Customer entity to be converted.
     * @return The corresponding CustomerDTO object.
     */
    CustomerDTO customerToCustomerDTO(Customer customer);
}