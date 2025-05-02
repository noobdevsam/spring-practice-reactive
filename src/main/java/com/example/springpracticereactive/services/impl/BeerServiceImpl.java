package com.example.springpracticereactive.services.impl;

import com.example.springpracticereactive.domain.Beer;
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

	@Override
	public Mono<BeerDTO> updateBeer(Integer id, BeerDTO beerDTO) {
		return beerRepository.findById(id).flatMap(
			foundBeer -> {
				// Create a new Beer instance with updated fields but preserve the ID and timestamps
				return beerRepository.save(
					new Beer(
						foundBeer.id(),
						beerDTO.beerName(),
						beerDTO.beerStyle(),
						beerDTO.upc(),
						beerDTO.quantityOnHand() != null ? Integer.valueOf(beerDTO.quantityOnHand()) : foundBeer.quantityOnHand(),
						beerDTO.price(),
						foundBeer.createdDate(),
						foundBeer.lastModifiedDate()
					)
				);
			}).map(beerMapper::beerToBeerDTO);

		// The flatMap operator is then used to process the emitted beer entity.
		/*
		 * Inside the flatMap block, a new Beer instance is created to reflect the updated values.
		 * Since both the Beer entity and BeerDTO are implemented as immutable records,
		 * the existing beer cannot be modified directly. Instead, a new Beer object is constructed
		 * using the values from the BeerDTO and the existing beer.
		 * */

		/*
		 * The newly created Beer instance is then saved to the database using beerRepository.save(updatedBeer),
		 * which returns a Mono<Beer> containing the saved entity.
		 * Finally, the saved Beer entity is mapped back to a BeerDTO using the beerMapper.beerToBeerDTO method.
		 * This mapping is performed using the map operator, which transforms the emitted Beer into a BeerDTO
		 * before returning it to the caller.
		 * */
	}
	
	@Override
	public Mono<BeerDTO> patchBeer(Integer id, BeerDTO beerDTO) {
		return beerRepository.findById(id)
			       .flatMap(foundBeer -> {
				       // Create a new Beer with conditionally updated fields
				       return beerRepository.save(new Beer(
					       foundBeer.id(),
					       beerDTO.beerName() != null ? beerDTO.beerName() : foundBeer.beerName(),
					       beerDTO.beerStyle() != null ? beerDTO.beerStyle() : foundBeer.beerStyle(),
					       beerDTO.upc() != null ? beerDTO.upc() : foundBeer.upc(),
					       beerDTO.quantityOnHand() != null ? Integer.valueOf(beerDTO.quantityOnHand()) : foundBeer.quantityOnHand(),
					       beerDTO.price() != null ? beerDTO.price() : foundBeer.price(),
					       foundBeer.createdDate(),
					       foundBeer.lastModifiedDate())
				       );
			       })
			       .map(beerMapper::beerToBeerDTO);
		
		// Since records don't support setter methods, we use the ternary operator pattern to only update fields when
		// the DTO provides non-null values.
	}
	
	@Override
	public Mono<Void> deleteBeerById(Integer id) {
		return beerRepository.deleteById(id);
	}
	
}
