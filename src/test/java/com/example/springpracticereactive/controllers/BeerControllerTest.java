package com.example.springpracticereactive.controllers;

import com.example.springpracticereactive.model.BeerDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureWebTestClient
class BeerControllerTest {
	
	@Autowired
	WebTestClient webTestClient;
	
	@Test
	void test_list_beers() {
		webTestClient.get()
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
	
	@Test
	void test_get_beer_by_id() {
		webTestClient.get()
			.uri(BeerController.BEER_PATH_ID, 1)
			.exchange()
			.expectStatus().isOk()
			.expectHeader().valueEquals("content-type", "application/json")
			.expectBody(BeerDTO.class);
	}
}