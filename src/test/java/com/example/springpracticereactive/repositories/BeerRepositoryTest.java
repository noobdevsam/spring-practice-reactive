package com.example.springpracticereactive.repositories;

import com.example.springpracticereactive.config.DatabaseConfig;
import com.example.springpracticereactive.domain.Beer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;

/**
 * Test class for the BeerRepository.
 * This class contains unit tests to verify the functionality of BeerRepository.
 */
@DataR2dbcTest
@Import(DatabaseConfig.class)
public class BeerRepositoryTest {

    /**
     * Autowired instance of BeerRepository.
     * Used to perform database operations in the tests.
     */
    @Autowired
    BeerRepository beerRepository;

    /**
     * Helper method to create a new Beer object.
     *
     * @return a new Beer instance with predefined values.
     */
    public static Beer getNewBeer() {
        return new Beer("Space Dust", // Name of the beer
                "IPA",                   // Style of the beer
                "2316345",               // UPC code of the beer
                45,                      // Quantity on hand
                new BigDecimal("3.99")   // Price of the beer
        );
    }

    /**
     * Test method to verify saving a new Beer entity.
     * This method saves a new Beer object to the database and prints the saved entity.
     */
    @Test
    void test_save_new_beer() {
        beerRepository.save(getNewBeer())
                .subscribe(beer -> System.out.println(beer.toString()));
    }
}