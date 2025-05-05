package com.example.springpracticereactive.model;

import java.time.LocalDateTime;

public record CustomerDTO(
	Integer id,
	String customerName,
	LocalDateTime createdDate,
	LocalDateTime lastModifiedDate
) {
	public CustomerDTO(
		String customerName
	) {
		this(null, customerName, null, null);
	}
}