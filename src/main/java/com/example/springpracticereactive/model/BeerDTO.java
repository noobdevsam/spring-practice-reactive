package com.example.springpracticereactive.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BeerDTO(
	Integer id,
	
	@NotBlank
	@Size(min = 3, max = 255)
	String beerName,
	
	@Size(min = 1, max = 255)
	String beerStyle,
	
	@Size(max = 25)
	String upc,
	String quantityOnHand,
	BigDecimal price,
	LocalDateTime createdDate,
	LocalDateTime lastModifiedDate
) {
	public BeerDTO(String beerName) {
		this(null, beerName, null, null, null, null, null, null);
	}
//
//	public BeerDTO(Integer id, String beerName, String beerStyle, String upc, String quantityOnHand, BigDecimal price) {
//		this(id, beerName, beerStyle, upc, quantityOnHand, price, null, null);
//	}
//
//	public BeerDTO(String beerName, String beerStyle, String upc, String quantityOnHand, BigDecimal price) {
//		this(0, beerName, beerStyle, upc, quantityOnHand, price, null, null);
//	}
}
