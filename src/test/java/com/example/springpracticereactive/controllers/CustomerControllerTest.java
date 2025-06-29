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

import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockOAuth2Login;

/**
 * Test class for CustomerController.
 * Contains integration tests for CustomerController endpoints using WebTestClient.
 */
@SpringBootTest
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerControllerTest {

    @Autowired
    WebTestClient webTestClient;

    /**
     * Utility method to create a test CustomerDTO object.
     *
     * @return a new CustomerDTO instance with test data.
     */
    public static CustomerDTO getCustomerDTO() {
        return new CustomerDTO("Test Customer");
    }

    /**
     * Test for listing all customers.
     * Verifies the response status, content type, and the size of the returned list.
     */
    @Order(1)
    @Test
    void listCustomers() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .get()
                .uri(CustomerController.CUSTOMER_PATH)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("content-type", "application/json")
                .expectBody().jsonPath("$.size()").isEqualTo(3);
    }

    /**
     * Test for retrieving a customer by its ID.
     * Verifies the response status, content type, and the returned CustomerDTO object.
     */
    @Order(2)
    @Test
    void getCustomerById() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .get()
                .uri(CustomerController.CUSTOMER_PATH_ID, 1)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("content-type", "application/json")
                .expectBody(CustomerDTO.class);
    }

    /**
     * Test for updating an existing customer.
     * Verifies the response status is No Content.
     */
    @Order(4)
    @Test
    void updateCustomer() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .put()
                .uri(CustomerController.CUSTOMER_PATH_ID, 1)
                .body(Mono.just(getCustomerDTO()), CustomerDTO.class)
                .exchange()
                .expectStatus().isNoContent();
    }

    /**
     * Test for deleting a customer by its ID.
     * Verifies the response status is No Content.
     */
    @Order(5)
    @Test
    void deleteCustomerById() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .delete()
                .uri(CustomerController.CUSTOMER_PATH_ID, 1)
                .exchange()
                .expectStatus().isNoContent();
    }

    /**
     * Test for creating a new customer.
     * Verifies the response status, location header, and the creation of the customer.
     */
    @Order(3)
    @Test
    void createNewCustomer() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .post()
                .uri(CustomerController.CUSTOMER_PATH)
                .body(Mono.just(getCustomerDTO()), CustomerDTO.class)
                .header("content-type", "application/json")
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().location("http://localhost:8080/api/v2/customer/4");
    }

    /**
     * Test for retrieving a customer by an ID that does not exist.
     * Verifies the response status is Not Found.
     */
    @Order(6)
    @Test
    void test_get_by_id_not_found() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .get()
                .uri(CustomerController.CUSTOMER_PATH_ID, 100)
                .exchange()
                .expectStatus().isNotFound();
    }

    /**
     * Test for updating a customer by an ID that does not exist.
     * Verifies the response status is Not Found.
     */
    @Order(7)
    @Test
    void test_update_not_found() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .put()
                .uri(CustomerController.CUSTOMER_PATH_ID, 100)
                .body(Mono.just(getCustomerDTO()), CustomerDTO.class)
                .exchange()
                .expectStatus().isNotFound();
    }

    /**
     * Test for updating a customer with invalid data.
     * Verifies the response status is Bad Request.
     */
    @Order(8)
    @Test
    void test_update_bad_data() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .put()
                .uri(CustomerController.CUSTOMER_PATH_ID, 1)
                .body(Mono.just(new CustomerDTO("")), CustomerDTO.class)
                .exchange()
                .expectStatus().isBadRequest();
    }

    /**
     * Test for patching a customer by an ID that does not exist.
     * Verifies the response status is Not Found.
     */
    @Order(9)
    @Test
    void test_patch_id_not_found() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .patch()
                .uri(CustomerController.CUSTOMER_PATH_ID, 100)
                .body(Mono.just(getCustomerDTO()), CustomerDTO.class)
                .exchange()
                .expectStatus().isNotFound();
    }

    /**
     * Test for deleting a customer by an ID that does not exist.
     * Verifies the response status is Not Found.
     */
    @Order(10)
    @Test
    void test_delete_not_found() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .delete()
                .uri(CustomerController.CUSTOMER_PATH_ID, 100)
                .exchange()
                .expectStatus().isNotFound();
    }
}