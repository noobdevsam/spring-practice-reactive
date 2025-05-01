package com.example.springpracticereactive.controllers;

import com.example.springpracticereactive.model.BeerDTO;
import com.example.springpracticereactive.services.BeerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class BeerController {

	public static final String BEER_PATH = "/api/v2/beer";
	public static final String BEER_PATH_ID = BEER_PATH + "/{id}";
	private final BeerService beerService;

	public BeerController(BeerService beerService) {
		this.beerService = beerService;
	}

	@GetMapping(BEER_PATH)
	Flux<BeerDTO> listBeers() {
		return beerService.listBeers();
	}

	@GetMapping(BEER_PATH_ID)
	Mono<BeerDTO> getBeerById(@PathVariable Integer id) {
		return beerService.getBeerById(id);
	}

}
