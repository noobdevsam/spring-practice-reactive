package com.example.springpracticereactive.services;

import com.example.springpracticereactive.model.BeerDTO;
import reactor.core.publisher.Flux;

public interface BeerService {

	Flux<BeerDTO> listBeers();

}
