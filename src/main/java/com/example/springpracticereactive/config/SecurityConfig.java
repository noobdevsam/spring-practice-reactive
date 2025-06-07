package com.example.springpracticereactive.config;

import org.springframework.boot.actuate.autoconfigure.security.reactive.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    /**
     * Configures a `SecurityWebFilterChain` specifically for securing actuator endpoints.
     * <p>
     * This filter chain is applied with the highest priority (`@Order(1)`).
     * It allows unrestricted access to all actuator endpoints by permitting all exchanges.
     *
     * @param http the `ServerHttpSecurity` object used to configure security for web requests
     * @return a `SecurityWebFilterChain` that permits all access to actuator endpoints
     * @throws Exception if an error occurs during the configuration
     */
    @Bean
    @Order(1)
    public SecurityWebFilterChain actuatorSecurityFilterChain(ServerHttpSecurity http) throws Exception {
        return http
                // Matches requests to any actuator endpoint
                .securityMatcher(
                        EndpointRequest.toAnyEndpoint()
                )
                // Permits all exchanges (no authentication required)
                .authorizeExchange(
                        auth -> auth.anyExchange().permitAll()
                )
                // Builds and returns the configured SecurityWebFilterChain
                .build();
    }

    /**
     * Configures a `SecurityWebFilterChain` for securing all other endpoints.
     * <p>
     * This filter chain is applied with the second highest priority (`@Order(2)`).
     * It enforces authentication for all exchanges and configures the application
     * to use JWT-based OAuth2 resource server support. CSRF protection is disabled.
     *
     * @param http the `ServerHttpSecurity` object used to configure security for web requests
     * @return a `SecurityWebFilterChain` that enforces authentication and uses JWT for OAuth2
     */
    @Bean
    @Order(2)
    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                // Requires authentication for all exchanges
                .authorizeExchange(
                        exchange -> exchange.anyExchange().authenticated()
                )
                // Configures OAuth2 resource server with JWT support
                .oauth2ResourceServer(
                        oAuth2ResourceServerSpec -> oAuth2ResourceServerSpec.jwt(Customizer.withDefaults())
                )
                // Disables CSRF protection
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                // Builds and returns the configured SecurityWebFilterChain
                .build();
    }
}
