package com.example.springpracticereactive.model;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for Customer.
 * Represents the data structure used to transfer customer-related information between layers.
 * This class is implemented as a Java record, which is a compact and immutable data structure.
 */
public record CustomerDTO(

        /**
         * The unique identifier for the customer.
         */
        Integer id,

        /**
         * The name of the customer.
         * Must not be blank, enforced by the @NotBlank annotation.
         */
        @NotBlank
        String customerName,

        /**
         * The date and time when the customer record was created.
         */
        LocalDateTime createdDate,

        /**
         * The date and time when the customer record was last modified.
         */
        LocalDateTime lastModifiedDate
) {
    /**
     * Constructor for creating a new CustomerDTO with only a name.
     * Other fields are set to null.
     *
     * @param customerName The name of the customer.
     */
    public CustomerDTO(String customerName) {
        this(null, customerName, null, null);
    }
}