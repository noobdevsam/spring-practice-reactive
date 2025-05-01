package com.example.springpracticereactive.controllers;

import com.example.springpracticereactive.model.BeerDTO;
import com.example.springpracticereactive.services.BeerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class BeerController {

	public static final String BEER_PATH = "/api/v2/beer";
	private final BeerService beerService;

	public BeerController(BeerService beerService) {
		this.beerService = beerService;
	}

	@GetMapping(BEER_PATH)
	Flux<BeerDTO> listBeers() {
		return beerService.listBeers();
	}
}
