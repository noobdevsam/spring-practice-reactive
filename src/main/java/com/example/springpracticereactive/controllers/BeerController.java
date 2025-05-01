package com.example.springpracticereactive.controllers;

import com.example.springpracticereactive.model.BeerDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class BeerController {

	public static final String BEER_PATH = "/api/v2/beer";

	@GetMapping(BEER_PATH)
	Flux<BeerDTO> listBeers() {
		return Flux.just(
			new BeerDTO(1), new BeerDTO(2)
		);
	}
}
