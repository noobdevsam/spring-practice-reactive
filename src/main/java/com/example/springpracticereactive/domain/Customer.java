package com.example.springpracticereactive.domain;

import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

public record Customer(
	
	@Id
	Integer id,
	
	@Size(max = 255)
	String customerName,
	
	@CreatedDate
	LocalDateTime createdDate,
	
	@LastModifiedDate
	LocalDateTime lastModifiedDate
) {
	public Customer(String customerName) {
		this(null, customerName, null, null);
	}
}