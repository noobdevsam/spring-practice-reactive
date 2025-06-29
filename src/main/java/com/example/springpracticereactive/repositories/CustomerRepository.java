package com.example.springpracticereactive.repositories;

import com.example.springpracticereactive.domain.Customer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

/**
 * Repository interface for Customer entities.
 * Extends ReactiveCrudRepository to provide reactive CRUD operations.
 *
 * @see org.springframework.data.repository.reactive.ReactiveCrudRepository
 */
public interface CustomerRepository extends ReactiveCrudRepository<Customer, Integer> {
    // No additional methods are defined, relying on the default CRUD operations provided by ReactiveCrudRepository.
}