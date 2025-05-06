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

@RestController
public class CustomerController {
	
	public static final String CUSTOMER_PATH = "/api/v2/customer";
	public static final String CUSTOMER_PATH_ID = CUSTOMER_PATH + "/{id}";
	private final CustomerService customerService;
	
	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	@GetMapping(CUSTOMER_PATH)
	Flux<CustomerDTO> listCustomers() {
		return customerService.listCustomers();
	}
	
	@GetMapping(CUSTOMER_PATH_ID)
	Mono<CustomerDTO> getCustomerById(@PathVariable Integer id) {
		return customerService.getCustomerById(id);
	}
	
	@PostMapping(CUSTOMER_PATH)
	Mono<ResponseEntity<Void>> createNewCustomer(@Validated @RequestBody CustomerDTO customerDTO) {
		return customerService.saveNewCustomer(customerDTO)
			       .map(savedDTO -> ResponseEntity.created(
				       UriComponentsBuilder.fromUriString("http://localhost:8080/" + CUSTOMER_PATH + "/" + savedDTO.id()).build().toUri()
				       ).build()
			       );
	}
	
	@PutMapping(CUSTOMER_PATH_ID)
	Mono<ResponseEntity<Void>> updateCustomer(@PathVariable Integer id, @Validated @RequestBody CustomerDTO customerDTO) {
		return customerService.updateCustomer(id, customerDTO)
			       .switchIfEmpty(
				       Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND))
			       )
			       .map(_ -> ResponseEntity.noContent().build());
	}
	
	@PatchMapping(CUSTOMER_PATH_ID)
	Mono<ResponseEntity<Void>> patchCustomer(@PathVariable Integer id, @Validated @RequestBody CustomerDTO customerDTO) {
		return customerService.patchCustomer(id, customerDTO)
			       .switchIfEmpty(
				       Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND))
			       )
			       .map(_ -> ResponseEntity.ok().build());
	}
	
	@DeleteMapping(CUSTOMER_PATH_ID)
	Mono<ResponseEntity<Void>> deleteCustomerById(@PathVariable Integer id) {
		return customerService.getCustomerById(id)
			       .switchIfEmpty(
				       Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND))
			       )
			       .flatMap(
				       customerDTO -> customerService.deleteCustomerById(customerDTO.id())
			       )
			       .thenReturn(
				       ResponseEntity.noContent().build()
			       );
	}
}