package com.example.springpracticereactive.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for Beer.
 * Represents the data structure used to transfer beer-related information between layers.
 * This class is implemented as a Java record, which is a compact and immutable data structure.
 */
public record BeerDTO(

        /**
         * The unique identifier for the beer.
         */
        Integer id,

        /**
         * The name of the beer.
         * Must be at least 3 characters and no more than 255 characters, enforced by @NotBlank and @Size annotations.
         */
        @NotBlank
        @Size(min = 3, max = 255)
        String beerName,

        /**
         * The style of the beer.
         * Must be between 1 and 255 characters, enforced by the @Size annotation.
         */
        @Size(min = 1, max = 255)
        String beerStyle,

        /**
         * The Universal Product Code (UPC) for the beer.
         * Must not exceed 25 characters, enforced by the @Size annotation.
         */
        @Size(max = 25)
        String upc,

        /**
         * The quantity of beer on hand.
         * Represented as a string for flexibility.
         */
        String quantityOnHand,

        /**
         * The price of the beer.
         * Represented as a BigDecimal for precision.
         */
        BigDecimal price,

        /**
         * The date and time when the beer record was created.
         */
        LocalDateTime createdDate,

        /**
         * The date and time when the beer record was last modified.
         */
        LocalDateTime lastModifiedDate
) {
    /**
     * Constructor for creating a new BeerDTO with only a name.
     * Other fields are set to null.
     *
     * @param beerName The name of the beer.
     */
    public BeerDTO(String beerName) {
        this(null, beerName, null, null, null, null, null, null);
    }

    // Uncommented constructors for additional flexibility in creating BeerDTO objects.
    // public BeerDTO(Integer id, String beerName, String beerStyle, String upc, String quantityOnHand, BigDecimal price) {
    //     this(id, beerName, beerStyle, upc, quantityOnHand, price, null, null);
    // }
    //
    // public BeerDTO(String beerName, String beerStyle, String upc, String quantityOnHand, BigDecimal price) {
    //     this(0, beerName, beerStyle, upc, quantityOnHand, price, null, null);
    // }
}