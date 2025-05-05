package com.example.springpracticereactive.controllers;

import com.example.springpracticereactive.model.BeerDTO;
import com.example.springpracticereactive.services.BeerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
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
		return beerService.getBeerById(id)
			       .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
	}

	@PostMapping(BEER_PATH)
	Mono<ResponseEntity<Void>> createNewBeer(@Validated @RequestBody BeerDTO beerDTO) {
		return beerService.saveNewBeer(beerDTO)
			       .map(savedDTO -> ResponseEntity.created(
				       UriComponentsBuilder.fromUriString("http://localhost:8080/" + BEER_PATH + "/" + savedDTO.id()).build().toUri()
				       ).build()
			       );
	}

	@PutMapping(BEER_PATH_ID)
	Mono<ResponseEntity<Void>> updateBeer(@PathVariable Integer id, @Validated @RequestBody BeerDTO beerDTO) {
		return beerService.updateBeer(id, beerDTO)
			       .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
			       .map(_ -> ResponseEntity.noContent().build());
	}
	
	@PatchMapping(BEER_PATH_ID)
	Mono<ResponseEntity<Void>> patchBeer(@PathVariable Integer id, @Validated @RequestBody BeerDTO beerDTO) {
		return beerService.patchBeer(id, beerDTO)
			       .map(_ -> ResponseEntity.ok().build());
	}
	
	@DeleteMapping(BEER_PATH_ID)
	Mono<ResponseEntity<Void>> deleteBeerById(@PathVariable Integer id) {
		return beerService.deleteBeerById(id)
			       .thenReturn(
				       ResponseEntity.noContent().build()
			       );
	}

}
