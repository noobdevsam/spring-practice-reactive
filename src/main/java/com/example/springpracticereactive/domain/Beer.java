package com.example.springpracticereactive.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Beer is a record that represents a Beer entity in the application.
 * It includes details such as name, style, UPC, quantity, price, and timestamps.
 */
public record Beer(

        /**
         * The unique identifier for the Beer entity.
         */
        @Id
        Integer id,

        /**
         * The name of the beer.
         */
        String beerName,

        /**
         * The style of the beer (e.g., IPA, Pale Ale).
         */
        String beerStyle,

        /**
         * The Universal Product Code (UPC) for the beer.
         */
        String upc,

        /**
         * The quantity of beer available on hand.
         */
        Integer quantityOnHand,

        /**
         * The price of the beer.
         */
        BigDecimal price,

        /**
         * The timestamp when the Beer entity was created.
         */
        @CreatedDate
        LocalDateTime createdDate,

        /**
         * The timestamp when the Beer entity was last modified.
         */
        @LastModifiedDate
        LocalDateTime lastModifiedDate
) {
    /**
     * Constructor for creating a Beer entity without an ID or timestamps.
     *
     * @param beerName       The name of the beer.
     * @param beerStyle      The style of the beer.
     * @param upc            The Universal Product Code (UPC) for the beer.
     * @param quantityOnHand The quantity of beer available on hand.
     * @param price          The price of the beer.
     */
    public Beer(
            String beerName,
            String beerStyle,
            String upc,
            Integer quantityOnHand,
            BigDecimal price
    ) {
        this(null, beerName, beerStyle, upc, quantityOnHand, price, null, null);
    }
}