package com.example.springpracticereactive.domain;

import org.springframework.data.annotation.Id;

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
	LocalDateTime createdDate,
	LocalDateTime lastModifiedDate
) {
}
