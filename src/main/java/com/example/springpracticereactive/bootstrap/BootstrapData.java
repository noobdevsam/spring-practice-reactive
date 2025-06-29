package com.example.springpracticereactive.bootstrap;

import com.example.springpracticereactive.domain.Beer;
import com.example.springpracticereactive.domain.Customer;
import com.example.springpracticereactive.repositories.BeerRepository;
import com.example.springpracticereactive.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * BootstrapData is a Spring Component that initializes the database with sample data
 * for Beer and Customer entities when the application starts.
 */
@Component
public class BootstrapData implements CommandLineRunner {

    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;

    /**
     * Constructor for BootstrapData.
     *
     * @param beerRepository     Repository for Beer entities.
     * @param customerRepository Repository for Customer entities.
     */
    public BootstrapData(BeerRepository beerRepository, CustomerRepository customerRepository) {
        this.beerRepository = beerRepository;
        this.customerRepository = customerRepository;
    }

    /**
     * Executes the data initialization logic when the application starts.
     *
     * @param args Command-line arguments passed to the application.
     * @throws Exception if an error occurs during execution.
     */
    @Override
    public void run(String... args) throws Exception {
        loadBeerData();
        loadCustomerData();

        // Log the count and details of Beer entities in the database.
        beerRepository.count().subscribe(count -> System.out.println("Beer Count: " + count));
        beerRepository.findAll().subscribe(beer -> System.out.println("Beer: " + beer));

        System.out.println();

        // Log the count and details of Customer entities in the database.
        customerRepository.count().subscribe(count -> System.out.println("Customer Count: " + count));
        customerRepository.findAll().subscribe(customer -> System.out.println("Customer: " + customer));
    }

    /**
     * Loads sample Beer data into the database if no Beer entities exist.
     */
    private void loadBeerData() {
        beerRepository.count().subscribe(count -> {
            if (count == 0) {
                var beer1 = new Beer(
                        "Galaxy Cat",
                        "Pale Ale",
                        "12346",
                        454,
                        new BigDecimal("12.99")
                );

                var beer2 = new Beer(
                        "Cranky Pants",
                        "Pale Ale",
                        "129686",
                        84,
                        new BigDecimal("11.99")
                );

                var beer3 = new Beer(
                        "Sunshine City",
                        "IPA",
                        "129816",
                        94,
                        new BigDecimal("14.99")
                );

                // Save sample Beer entities to the database.
                beerRepository.save(beer1).subscribe();
                beerRepository.save(beer2).subscribe();
                beerRepository.save(beer3).subscribe();
            }
        });
    }

    /**
     * Loads sample Customer data into the database if no Customer entities exist.
     */
    private void loadCustomerData() {
        customerRepository.count().subscribe(count -> {
            if (count == 0) {
                // Save sample Customer entities to the database.
                customerRepository.save(new Customer("John Doe")).subscribe();
                customerRepository.save(new Customer("Jane Smith")).subscribe();
                customerRepository.save(new Customer("Bob Johnson")).subscribe();
            }
        });
    }
}