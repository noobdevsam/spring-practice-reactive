package com.example.springpracticereactive.domain;

import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

/**
 * Represents a Customer entity in the system.
 * This class is implemented as a Java record, which is a compact and immutable data structure.
 * It includes fields for the customer's ID, name, creation date, and last modification date.
 */
public record Customer(

        /**
         * The unique identifier for the customer.
         * Annotated with @Id to indicate it is the primary key in the database.
         */
        @Id
        Integer id,

        /**
         * The name of the customer.
         * Must not exceed 255 characters, enforced by the @Size annotation.
         */
        @Size(max = 255)
        String customerName,

        /**
         * The date and time when the customer record was created.
         * Automatically populated by Spring Data using the @CreatedDate annotation.
         */
        @CreatedDate
        LocalDateTime createdDate,

        /**
         * The date and time when the customer record was last modified.
         * Automatically updated by Spring Data using the @LastModifiedDate annotation.
         */
        @LastModifiedDate
        LocalDateTime lastModifiedDate
) {
    /**
     * Constructor for creating a new Customer with only a name.
     * The ID, creation date, and last modification date are set to null.
     *
     * @param customerName The name of the customer.
     */
    public Customer(String customerName) {
        this(null, customerName, null, null);
    }
}