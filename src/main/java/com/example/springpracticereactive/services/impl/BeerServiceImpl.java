package com.example.springpracticereactive.services.impl;

import com.example.springpracticereactive.mappers.BeerMapper;
import com.example.springpracticereactive.model.BeerDTO;
import com.example.springpracticereactive.repositories.BeerRepository;
import com.example.springpracticereactive.services.BeerService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

	@Override
	public Mono<BeerDTO> getBeerById(Integer id) {
		return beerRepository.findById(id)
			       .map(beerMapper::beerToBeerDTO);
	}

	@Override
	public Mono<BeerDTO> saveNewBeer(BeerDTO beerDTO) {

		// It first maps this BeerDTO to a Beer entity using the beerDTOToBeer method from the BeerMapper

		// After the entity is saved, the resulting Mono<Beer> is transformed back into a Mono<BeerDTO>
		// using the beerToBeerDTO method from the BeerMapper
		return beerRepository.save(beerMapper.beerDTOToBeer(beerDTO))
			       .map(beerMapper::beerToBeerDTO);
	}
}
