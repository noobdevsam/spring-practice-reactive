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

/**
 * BeerController is a REST controller that handles HTTP requests for managing Beer entities.
 * It provides endpoints for CRUD operations using reactive programming with Project Reactor.
 */
@RestController
public class BeerController {

    /**
     * Base path for Beer-related API endpoints.
     */
    public static final String BEER_PATH = "/api/v2/beer";

    /**
     * Path for Beer-related API endpoints with an ID parameter.
     */
    public static final String BEER_PATH_ID = BEER_PATH + "/{id}";

    private final BeerService beerService;

    /**
     * Constructor for BeerController.
     *
     * @param beerService Service layer for Beer-related operations.
     */
    public BeerController(BeerService beerService) {
        this.beerService = beerService;
    }

    /**
     * Handles GET requests to list all Beer entities.
     *
     * @return A Flux stream of BeerDTO objects.
     */
    @GetMapping(BEER_PATH)
    Flux<BeerDTO> listBeers() {
        return beerService.listBeers();
    }

    /**
     * Handles GET requests to retrieve a Beer entity by its ID.
     *
     * @param id The ID of the Beer entity to retrieve.
     * @return A Mono containing the BeerDTO object, or an error if not found.
     */
    @GetMapping(BEER_PATH_ID)
    Mono<BeerDTO> getBeerById(@PathVariable Integer id) {
        return beerService.getBeerById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    /**
     * Handles POST requests to create a new Beer entity.
     *
     * @param beerDTO The BeerDTO object containing the details of the Beer to create.
     * @return A Mono containing a ResponseEntity with the location of the created Beer.
     */
    @PostMapping(BEER_PATH)
    Mono<ResponseEntity<Void>> createNewBeer(@Validated @RequestBody BeerDTO beerDTO) {
        return beerService.saveNewBeer(beerDTO)
                .map(savedDTO -> ResponseEntity.created(
                        UriComponentsBuilder.fromUriString("http://localhost:8080/" + BEER_PATH + "/" + savedDTO.id()).build().toUri()
                ).build());
    }

    /**
     * Handles PUT requests to update an existing Beer entity.
     *
     * @param id      The ID of the Beer entity to update.
     * @param beerDTO The BeerDTO object containing the updated details.
     * @return A Mono containing a ResponseEntity indicating the update status.
     */
    @PutMapping(BEER_PATH_ID)
    Mono<ResponseEntity<Void>> updateBeer(@PathVariable Integer id, @Validated @RequestBody BeerDTO beerDTO) {
        return beerService.updateBeer(id, beerDTO)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(_ -> ResponseEntity.noContent().build());
    }

    /**
     * Handles PATCH requests to partially update an existing Beer entity.
     *
     * @param id      The ID of the Beer entity to patch.
     * @param beerDTO The BeerDTO object containing the partial updates.
     * @return A Mono containing a ResponseEntity indicating the patch status.
     */
    @PatchMapping(BEER_PATH_ID)
    Mono<ResponseEntity<Void>> patchBeer(@PathVariable Integer id, @Validated @RequestBody BeerDTO beerDTO) {
        return beerService.patchBeer(id, beerDTO)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(_ -> ResponseEntity.ok().build());
    }

    /**
     * Handles DELETE requests to remove a Beer entity by its ID.
     *
     * @param id The ID of the Beer entity to delete.
     * @return A Mono containing a ResponseEntity indicating the deletion status.
     */
    @DeleteMapping(BEER_PATH_ID)
    Mono<ResponseEntity<Void>> deleteBeerById(@PathVariable Integer id) {
        return beerService.getBeerById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .flatMap(beerDTO -> beerService.deleteBeerById(beerDTO.id()))
                .thenReturn(ResponseEntity.noContent().build());
    }
}