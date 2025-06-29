package com.example.springpracticereactive.services;

import com.example.springpracticereactive.model.CustomerDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service interface for managing customers in a reactive Spring Boot application.
 * Provides methods for CRUD operations using reactive types.
 */
public interface CustomerService {

    /**
     * Lists all customers.
     *
     * @return a Flux stream of CustomerDTO objects representing all customers.
     */
    Flux<CustomerDTO> listCustomers();

    /**
     * Retrieves a customer by their ID.
     *
     * @param id the ID of the customer to retrieve.
     * @return a Mono containing the CustomerDTO object if found, or empty if not.
     */
    Mono<CustomerDTO> getCustomerById(Integer id);

    /**
     * Saves a new customer.
     *
     * @param customerDTO the CustomerDTO object representing the customer to save.
     * @return a Mono containing the saved CustomerDTO object.
     */
    Mono<CustomerDTO> saveNewCustomer(CustomerDTO customerDTO);

    /**
     * Updates an existing customer.
     *
     * @param id          the ID of the customer to update.
     * @param customerDTO the CustomerDTO object containing updated customer data.
     * @return a Mono containing the updated CustomerDTO object.
     */
    Mono<CustomerDTO> updateCustomer(Integer id, CustomerDTO customerDTO);

    /**
     * Partially updates an existing customer.
     *
     * @param id          the ID of the customer to patch.
     * @param customerDTO the CustomerDTO object containing partial customer data.
     * @return a Mono containing the patched CustomerDTO object.
     */
    Mono<CustomerDTO> patchCustomer(Integer id, CustomerDTO customerDTO);

    /**
     * Deletes a customer by their ID.
     *
     * @param id the ID of the customer to delete.
     * @return a Mono signaling completion when the customer is deleted.
     */
    Mono<Void> deleteCustomerById(Integer id);
}