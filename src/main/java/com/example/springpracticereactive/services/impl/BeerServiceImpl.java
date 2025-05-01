package com.example.springpracticereactive.services.impl;

import com.example.springpracticereactive.mappers.BeerMapper;
import com.example.springpracticereactive.model.BeerDTO;
import com.example.springpracticereactive.repositories.BeerRepository;
import com.example.springpracticereactive.services.BeerService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class BeerServiceImpl implements BeerService {

	private final BeerRepository beerRepository;
	private final BeerMapper beerMapper;

	public BeerServiceImpl(BeerRepository beerRepository, BeerMapper beerMapper) {
		this.beerRepository = beerRepository;
		this.beerMapper = beerMapper;
	}

	@Override
	public Flux<BeerDTO> listBeers() {
		return beerRepository.findAll()
			       .map(beerMapper::beerToBeerDTO);
	}
}
