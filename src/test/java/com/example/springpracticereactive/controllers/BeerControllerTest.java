package com.example.springpracticereactive.controllers;

import com.example.springpracticereactive.model.BeerDTO;
import com.example.springpracticereactive.repositories.BeerRepositoryTest;
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

@SpringBootTest
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BeerControllerTest {
	
	@Autowired
	WebTestClient webTestClient;
	
	@Order(1)
	@Test
	void test_list_beers() {
		webTestClient
			.mutateWith(mockOAuth2Login())
			.get()
			.uri(BeerController.BEER_PATH)
			.exchange()
			.expectStatus().isOk()
			.expectHeader().valueEquals("content-type", "application/json")
			.expectBody().jsonPath("$.size()").isEqualTo(3);
		
		/*
		 * In the context of the test, the exchange() method sends the HTTP GET request to the specified
		 * URI (BeerController.BEER_PATH) and returns a ResponseSpec object. This object allows further assertions
		 * to be made on the response, such as verifying the status code, headers, and body content.
		 * */
	}
	
	@Order(2)
	@Test
	void test_get_beer_by_id() {
		webTestClient
			.mutateWith(mockOAuth2Login())
			.get()
			.uri(BeerController.BEER_PATH_ID, 1)
			.exchange()
			.expectStatus().isOk()
			.expectHeader().valueEquals("content-type", "application/json")
			.expectBody(BeerDTO.class);
	}
	
	@Order(3)
	@Test
	void test_create_new_beer() {
		webTestClient
			.mutateWith(mockOAuth2Login())
			.post()
			.uri(BeerController.BEER_PATH)
			.body(Mono.just(BeerRepositoryTest.getNewBeer()), BeerDTO.class)
			.header("content-type", "application/json")
			.exchange()
			.expectStatus().isCreated()
			.expectHeader().location("http://localhost:8080/api/v2/beer/4");
	}
	
	@Order(4)
	@Test
	void test_create_new_beer_with_bad_data() {
		var testBeer = new BeerDTO("");
		
		webTestClient
			.mutateWith(mockOAuth2Login())
			.post()
			.uri(BeerController.BEER_PATH)
			.body(Mono.just(testBeer), BeerDTO.class)
			.header("content-type", "application/json")
			.exchange()
			.expectStatus().isBadRequest();
	}
	
	@Order(5)
	@Test
	void test_update_beer() {
		webTestClient
			.mutateWith(mockOAuth2Login())
			.put()
			.uri(BeerController.BEER_PATH_ID, 1)
			.body(Mono.just(BeerRepositoryTest.getNewBeer()), BeerDTO.class)
			.exchange()
			.expectStatus().isNoContent();
	}
	
	@Order(6)
	@Test
	void test_update_beer_with_bad_data() {
		var testBeer = new BeerDTO("");
		
		webTestClient
			.mutateWith(mockOAuth2Login())
			.put()
			.uri(BeerController.BEER_PATH_ID, 1)
			.body(Mono.just(testBeer), BeerDTO.class)
			.exchange()
			.expectStatus().isBadRequest();
	}
	
	@Order(7)
	@Test
	void test_delete_beer() {
		webTestClient
			.mutateWith(mockOAuth2Login())
			.delete()
			.uri(BeerController.BEER_PATH_ID, 1)
			.exchange()
			.expectStatus().isNoContent();
	}
	
	@Order(8)
	@Test
	void test_get_by_id_not_found() {
		webTestClient
			.mutateWith(mockOAuth2Login())
			.get()
			.uri(BeerController.BEER_PATH_ID, 99)
			.exchange()
			.expectStatus().isNotFound();
	}
	
	@Order(9)
	@Test
	void test_update_beer_not_found() {
		webTestClient
			.mutateWith(mockOAuth2Login())
			.put()
			.uri(BeerController.BEER_PATH_ID, 99)
			.body(Mono.just(BeerRepositoryTest.getNewBeer()), BeerDTO.class)
			.exchange()
			.expectStatus().isNotFound();
	}
	
	@Order(10)
	@Test
	void test_patch_not_found() {
		webTestClient
			.mutateWith(mockOAuth2Login())
			.patch()
			.uri(BeerController.BEER_PATH_ID, 99)
			.body(Mono.just(BeerRepositoryTest.getNewBeer()), BeerDTO.class)
			.exchange()
			.expectStatus().isNotFound();
	}
	
	@Order(11)
	@Test
	void test_delete_not_found() {
		webTestClient
			.mutateWith(mockOAuth2Login())
			.delete()
			.uri(BeerController.BEER_PATH_ID, 99)
			.exchange()
			.expectStatus().isNotFound();
	}
	
}