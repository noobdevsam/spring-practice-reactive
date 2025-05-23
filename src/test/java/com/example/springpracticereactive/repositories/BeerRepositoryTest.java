package com.example.springpracticereactive.repositories;

import com.example.springpracticereactive.config.DatabaseConfig;
import com.example.springpracticereactive.domain.Beer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;

@DataR2dbcTest
@Import(DatabaseConfig.class)
public class BeerRepositoryTest {

	@Autowired
	BeerRepository beerRepository;

	@Test
	void test_save_new_beer() {
		beerRepository.save(getNewBeer())
			.subscribe(beer -> System.out.println(beer.toString()));
	}
	
	public static Beer getNewBeer() {
		return new Beer("Space Dust",
			"IPA",
			"2316345",
			45,
			new BigDecimal("3.99")
		);
	}
}