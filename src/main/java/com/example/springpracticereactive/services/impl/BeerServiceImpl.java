package com.example.springpracticereactive.services.impl;

import com.example.springpracticereactive.domain.Beer;
import com.example.springpracticereactive.mappers.BeerMapper;
import com.example.springpracticereactive.model.BeerDTO;
import com.example.springpracticereactive.repositories.BeerRepository;
import com.example.springpracticereactive.services.BeerService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Implementation of the BeerService interface for managing beers in a reactive Spring Boot application.
 * Provides methods for CRUD operations using reactive types.
 */
@Service
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    /**
     * Constructor for BeerServiceImpl.
     *
     * @param beerRepository the repository for Beer entities.
     * @param beerMapper     the mapper for converting between Beer and BeerDTO objects.
     */
    public BeerServiceImpl(BeerRepository beerRepository, BeerMapper beerMapper) {
        this.beerRepository = beerRepository;
        this.beerMapper = beerMapper;
    }

    /**
     * Lists all beers.
     *
     * @return a Flux stream of BeerDTO objects representing all beers.
     */
    @Override
    public Flux<BeerDTO> listBeers() {
        return beerRepository.findAll()
                .map(beerMapper::beerToBeerDTO);
    }

    /**
     * Retrieves a beer by its ID.
     *
     * @param id the ID of the beer to retrieve.
     * @return a Mono containing the BeerDTO object if found, or empty if not.
     */
    @Override
    public Mono<BeerDTO> getBeerById(Integer id) {
        return beerRepository.findById(id)
                .map(beerMapper::beerToBeerDTO);
    }

    /**
     * Saves a new beer.
     *
     * @param beerDTO the BeerDTO object representing the beer to save.
     * @return a Mono containing the saved BeerDTO object.
     */
    @Override
    public Mono<BeerDTO> saveNewBeer(BeerDTO beerDTO) {

        // It first maps this BeerDTO to a Beer entity using the beerDTOToBeer method from the BeerMapper

        // After the entity is saved, the resulting Mono<Beer> is transformed back into a Mono<BeerDTO>
        // using the beerToBeerDTO method from the BeerMapper
        return beerRepository.save(beerMapper.beerDTOToBeer(beerDTO))
                .map(beerMapper::beerToBeerDTO);
    }

    /**
     * Updates an existing beer.
     *
     * @param id      the ID of the beer to update.
     * @param beerDTO the BeerDTO object containing updated beer data.
     * @return a Mono containing the updated BeerDTO object.
     */
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

    /**
     * Partially updates an existing beer.
     *
     * @param id      the ID of the beer to patch.
     * @param beerDTO the BeerDTO object containing updated fields.
     * @return a Mono containing the patched BeerDTO object.
     */
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

    /**
     * Deletes a beer by its ID.
     *
     * @param id the ID of the beer to delete.
     * @return a Mono that completes when the deletion is done.
     */
    @Override
    public Mono<Void> deleteBeerById(Integer id) {
        return beerRepository.deleteById(id);
    }

}