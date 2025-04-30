package com.example.springpracticereactive.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Beer(

	@Id
	Integer id,
	String beerName,
	String beerStyle,
	String upc,
	Integer quantityOnHand,
	BigDecimal price,

	@CreatedDate
	LocalDateTime createdDate,

	@LastModifiedDate
	LocalDateTime lastModifiedDate
) {
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
