package com.example.springpracticereactive.services;

import com.example.springpracticereactive.model.BeerDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service interface for managing Beer entities.
 * Provides reactive methods for CRUD operations on Beer data.
 */
public interface BeerService {

    /**
     * Retrieves a list of all beers.
     *
     * @return A Flux stream of BeerDTO objects.
     */
    Flux<BeerDTO> listBeers();

    /**
     * Retrieves a beer by its unique identifier.
     *
     * @param id The unique identifier of the beer.
     * @return A Mono containing the BeerDTO object, or empty if not found.
     */
    Mono<BeerDTO> getBeerById(Integer id);

    /**
     * Saves a new beer entity.
     *
     * @param beerDTO The BeerDTO object containing the beer details to be saved.
     * @return A Mono containing the saved BeerDTO object.
     */
    Mono<BeerDTO> saveNewBeer(BeerDTO beerDTO);

    /**
     * Updates an existing beer entity.
     *
     * @param id      The unique identifier of the beer to be updated.
     * @param beerDTO The BeerDTO object containing the updated beer details.
     * @return A Mono containing the updated BeerDTO object.
     */
    Mono<BeerDTO> updateBeer(Integer id, BeerDTO beerDTO);

    /**
     * Partially updates an existing beer entity.
     *
     * @param id      The unique identifier of the beer to be patched.
     * @param beerDTO The BeerDTO object containing the partial updates.
     * @return A Mono containing the patched BeerDTO object.
     */
    Mono<BeerDTO> patchBeer(Integer id, BeerDTO beerDTO);

    /**
     * Deletes a beer entity by its unique identifier.
     *
     * @param id The unique identifier of the beer to be deleted.
     * @return A Mono signaling completion of the delete operation.
     */
    Mono<Void> deleteBeerById(Integer id);

}