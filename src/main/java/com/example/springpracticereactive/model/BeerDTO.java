package com.example.springpracticereactive.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BeerDTO(
	Integer id,
	String beerName,
	String beerStyle,
	String upc,
	String quantityOnHand,
	BigDecimal price,
	LocalDateTime createdDate,
	LocalDateTime lastUpdatedDate
) {
	public BeerDTO(Integer id) {
		this(id, null, null, null, null, null, null, null);
	}

	public BeerDTO(Integer id, String beerName, String beerStyle, String upc, String quantityOnHand, BigDecimal price) {
		this(id, beerName, beerStyle, upc, quantityOnHand, price, null, null);
	}

	public BeerDTO(String beerName, String beerStyle, String upc, String quantityOnHand, BigDecimal price) {
		this(0, beerName, beerStyle, upc, quantityOnHand, price, null, null);
	}
}
