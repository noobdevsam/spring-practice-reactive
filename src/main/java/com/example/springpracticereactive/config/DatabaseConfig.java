package com.example.springpracticereactive.config;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

@Configuration
public class DatabaseConfig {

	@Value("classpath:/schema.sql")
	Resource resource;


	// This setup ensures that the database schema is automatically initialized when the application starts,
	// making it easier to manage database migrations and setup in a reactive Spring Boot application.
	@Bean
	ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
		final var factory_initializer = new ConnectionFactoryInitializer();

		factory_initializer.setConnectionFactory(connectionFactory);


		factory_initializer.setDatabasePopulator(
			new ResourceDatabasePopulator(resource)
		);

		return factory_initializer;

	}


}
