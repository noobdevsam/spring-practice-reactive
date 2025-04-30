package com.example.springpracticereactive.bootstrap;

import com.example.springpracticereactive.domain.Beer;
import com.example.springpracticereactive.repositories.BeerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BootstrapData implements CommandLineRunner {

	private final BeerRepository beerRepository;

	public BootstrapData(BeerRepository beerRepository) {
		this.beerRepository = beerRepository;
	}


	@Override
	public void run(String... args) throws Exception {
		loadBeerData();

		beerRepository.count().subscribe(count -> System.out.println("Beer Count: " + count));
		beerRepository.findAll().subscribe(beer -> System.out.println("Beer: " + beer));
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
}
