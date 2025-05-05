package com.example.springpracticereactive.controllers;

import com.example.springpracticereactive.model.CustomerDTO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@SpringBootTest
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerControllerTest {
	
	@Autowired
	WebTestClient webTestClient;
	
	@Order(1)
	@Test
	void listCustomers() {
		webTestClient.get()
			.uri(CustomerController.CUSTOMER_PATH)
			.exchange()
			.expectStatus().isOk()
			.expectHeader().valueEquals("content-type", "application/json")
			.expectBody().jsonPath("$.size()").isEqualTo(3);
	}
	
	@Order(2)
	@Test
	void getCustomerById() {
		webTestClient.get()
			.uri(CustomerController.CUSTOMER_PATH_ID, 1)
			.exchange()
			.expectStatus().isOk()
			.expectHeader().valueEquals("content-type", "application/json")
			.expectBody(CustomerDTO.class);
	}
	
	public static CustomerDTO getCustomerDTO() {
		return new CustomerDTO("Test Customer");
	}
	
	@Test
	void updateCustomer() {
	}
	
	@Test
	void deleteCustomerById() {
	}
	
	@Order(3)
	@Test
	void createNewCustomer() {
		webTestClient.post()
			.uri(CustomerController.CUSTOMER_PATH)
			.body(Mono.just(getCustomerDTO()), CustomerDTO.class)
			.header("content-type", "application/json")
			.exchange()
			.expectStatus().isCreated()
			.expectHeader().location("http://localhost:8080/api/v2/customer/4");
	}
}