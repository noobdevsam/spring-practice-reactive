package com.example.springpracticereactive.repositories;

import com.example.springpracticereactive.domain.Beer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

/**
 * Repository interface for Beer entities.
 * Extends ReactiveCrudRepository to provide reactive CRUD operations.
 *
 * @see org.springframework.data.repository.reactive.ReactiveCrudRepository
 */
public interface BeerRepository extends ReactiveCrudRepository<Beer, Integer> {
    // No additional methods are defined, relying on the default CRUD operations provided by ReactiveCrudRepository.
}