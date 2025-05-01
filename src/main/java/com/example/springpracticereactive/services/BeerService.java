package com.example.springpracticereactive.services;

import com.example.springpracticereactive.model.BeerDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BeerService {

	Flux<BeerDTO> listBeers();

	Mono<BeerDTO> getBeerById(Integer id);

}
