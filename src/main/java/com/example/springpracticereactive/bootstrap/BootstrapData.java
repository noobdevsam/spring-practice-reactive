package com.example.springpracticereactive.bootstrap;

import com.example.springpracticereactive.domain.Beer;
import com.example.springpracticereactive.domain.Customer;
import com.example.springpracticereactive.repositories.BeerRepository;
import com.example.springpracticereactive.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BootstrapData implements CommandLineRunner {

	private final BeerRepository beerRepository;
	private final CustomerRepository customerRepository;
	
	public BootstrapData(BeerRepository beerRepository, CustomerRepository customerRepository) {
		this.beerRepository = beerRepository;
		this.customerRepository = customerRepository;
	}


	@Override
	public void run(String... args) throws Exception {
		loadBeerData();
		loadCustomerData();

		beerRepository.count().subscribe(count -> System.out.println("Beer Count: " + count));
		beerRepository.findAll().subscribe(beer -> System.out.println("Beer: " + beer));
		
		System.out.println();
		
		customerRepository.count().subscribe(count -> System.out.println("Customer Count: " + count));
		customerRepository.findAll().subscribe(customer -> System.out.println("Customer: " + customer));
	}
	
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

				beerRepository.save(beer1).subscribe();
				beerRepository.save(beer2).subscribe();
				beerRepository.save(beer3).subscribe();
			}
		});
	}
	
	private void loadCustomerData() {
		customerRepository.count().subscribe(count -> {
			if (count == 0) {
				customerRepository.save(new Customer("John Doe")).subscribe();
				customerRepository.save(new Customer("Jane Smith")).subscribe();
				customerRepository.save(new Customer("Bob Johnson")).subscribe();
			}
		});
	}
}
