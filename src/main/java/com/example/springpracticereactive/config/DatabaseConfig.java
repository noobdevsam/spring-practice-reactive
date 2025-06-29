package com.example.springpracticereactive.config;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

/**
 * DatabaseConfig is a Spring configuration class that sets up database initialization
 * and auditing for a reactive Spring Boot application.
 */
@Configuration
@EnableR2dbcAuditing
public class DatabaseConfig {

    /**
     * Resource representing the SQL schema file to initialize the database.
     * The file is located in the classpath.
     */
    @Value("classpath:/schema.sql")
    Resource resource;

    /**
     * Creates and configures a ConnectionFactoryInitializer bean to initialize the database schema.
     * This ensures the database schema is automatically populated when the application starts.
     *
     * @param connectionFactory The reactive connection factory used to connect to the database.
     * @return A configured ConnectionFactoryInitializer bean.
     */
    @Bean
    ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
        final var factory_initializer = new ConnectionFactoryInitializer();

        // Set the connection factory for the initializer.
        factory_initializer.setConnectionFactory(connectionFactory);

        // Set the database populator to use the provided SQL schema resource.
        factory_initializer.setDatabasePopulator(
                new ResourceDatabasePopulator(resource)
        );

        return factory_initializer;
    }
}