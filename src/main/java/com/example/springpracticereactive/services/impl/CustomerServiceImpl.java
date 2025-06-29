package com.example.springpracticereactive.services.impl;

import com.example.springpracticereactive.domain.Customer;
import com.example.springpracticereactive.mappers.CustomerMapper;
import com.example.springpracticereactive.model.CustomerDTO;
import com.example.springpracticereactive.repositories.CustomerRepository;
import com.example.springpracticereactive.services.CustomerService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Implementation of the CustomerService interface.
 * Provides reactive methods for managing customers.
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    /**
     * Constructor for CustomerServiceImpl.
     *
     * @param customerRepository the repository for customer data
     * @param customerMapper     the mapper for converting between Customer and CustomerDTO
     */
    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    /**
     * Lists all customers.
     *
     * @return a Flux of CustomerDTO objects
     */
    @Override
    public Flux<CustomerDTO> listCustomers() {
        return customerRepository.findAll()
                .map(customerMapper::customerToCustomerDTO);
    }

    /**
     * Retrieves a customer by their ID.
     *
     * @param id the ID of the customer
     * @return a Mono of CustomerDTO if found, otherwise an empty Mono
     */
    @Override
    public Mono<CustomerDTO> getCustomerById(Integer id) {
        return customerRepository.findById(id)
                .map(customerMapper::customerToCustomerDTO);
    }

    /**
     * Saves a new customer.
     *
     * @param customerDTO the data transfer object containing customer details
     * @return a Mono of the saved CustomerDTO
     */
    @Override
    public Mono<CustomerDTO> saveNewCustomer(CustomerDTO customerDTO) {
        return customerRepository.save(customerMapper.customerDTOToCustomer(customerDTO))
                .map(customerMapper::customerToCustomerDTO);
    }

    /**
     * Updates an existing customer.
     *
     * @param id          the ID of the customer to update
     * @param customerDTO the data transfer object containing updated customer details
     * @return a Mono of the updated CustomerDTO
     */
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

    /**
     * Partially updates a customer.
     *
     * @param id          the ID of the customer to patch
     * @param customerDTO the data transfer object containing partial updates
     * @return a Mono of the patched CustomerDTO
     */
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

    /**
     * Deletes a customer by their ID.
     *
     * @param id the ID of the customer to delete
     * @return a Mono signaling completion
     */
    @Override
    public Mono<Void> deleteCustomerById(Integer id) {
        return customerRepository.deleteById(id);
    }
}