package com.example.springpracticereactive.controllers;

import com.example.springpracticereactive.model.CustomerDTO;
import com.example.springpracticereactive.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * CustomerController is a REST controller that handles HTTP requests for managing Customer entities.
 * It provides endpoints for CRUD operations using reactive programming with Project Reactor.
 */
@RestController
public class CustomerController {

    /**
     * Base path for Customer-related API endpoints.
     */
    public static final String CUSTOMER_PATH = "/api/v2/customer";

    /**
     * Path for Customer-related API endpoints with an ID parameter.
     */
    public static final String CUSTOMER_PATH_ID = CUSTOMER_PATH + "/{id}";

    private final CustomerService customerService;

    /**
     * Constructor for CustomerController.
     *
     * @param customerService Service layer for Customer-related operations.
     */
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Handles GET requests to list all Customer entities.
     *
     * @return A Flux stream of CustomerDTO objects.
     */
    @GetMapping(CUSTOMER_PATH)
    Flux<CustomerDTO> listCustomers() {
        return customerService.listCustomers();
    }

    /**
     * Handles GET requests to retrieve a Customer entity by its ID.
     *
     * @param id The ID of the Customer entity to retrieve.
     * @return A Mono containing the CustomerDTO object, or an error if not found.
     */
    @GetMapping(CUSTOMER_PATH_ID)
    Mono<CustomerDTO> getCustomerById(@PathVariable Integer id) {
        return customerService.getCustomerById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    /**
     * Handles POST requests to create a new Customer entity.
     *
     * @param customerDTO The CustomerDTO object containing the details of the Customer to create.
     * @return A Mono containing a ResponseEntity with the location of the created Customer.
     */
    @PostMapping(CUSTOMER_PATH)
    Mono<ResponseEntity<Void>> createNewCustomer(@Validated @RequestBody CustomerDTO customerDTO) {
        return customerService.saveNewCustomer(customerDTO)
                .map(savedDTO -> ResponseEntity.created(
                        UriComponentsBuilder.fromUriString("http://localhost:8080/" + CUSTOMER_PATH + "/" + savedDTO.id()).build().toUri()
                ).build());
    }

    /**
     * Handles PUT requests to update an existing Customer entity.
     *
     * @param id          The ID of the Customer entity to update.
     * @param customerDTO The CustomerDTO object containing the updated details.
     * @return A Mono containing a ResponseEntity indicating the update status.
     */
    @PutMapping(CUSTOMER_PATH_ID)
    Mono<ResponseEntity<Void>> updateCustomer(@PathVariable Integer id, @Validated @RequestBody CustomerDTO customerDTO) {
        return customerService.updateCustomer(id, customerDTO)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(_ -> ResponseEntity.noContent().build());
    }

    /**
     * Handles PATCH requests to partially update an existing Customer entity.
     *
     * @param id          The ID of the Customer entity to patch.
     * @param customerDTO The CustomerDTO object containing the partial updates.
     * @return A Mono containing a ResponseEntity indicating the patch status.
     */
    @PatchMapping(CUSTOMER_PATH_ID)
    Mono<ResponseEntity<Void>> patchCustomer(@PathVariable Integer id, @Validated @RequestBody CustomerDTO customerDTO) {
        return customerService.patchCustomer(id, customerDTO)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(_ -> ResponseEntity.ok().build());
    }

    /**
     * Handles DELETE requests to remove a Customer entity by its ID.
     *
     * @param id The ID of the Customer entity to delete.
     * @return A Mono containing a ResponseEntity indicating the deletion status.
     */
    @DeleteMapping(CUSTOMER_PATH_ID)
    Mono<ResponseEntity<Void>> deleteCustomerById(@PathVariable Integer id) {
        return customerService.getCustomerById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .flatMap(customerDTO -> customerService.deleteCustomerById(customerDTO.id()))
                .thenReturn(ResponseEntity.noContent().build());
    }
}