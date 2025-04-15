package com.example.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoders;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;

import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
	
	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		http
		.csrf(ServerHttpSecurity.CsrfSpec::disable) // Disable CSRF
		.authorizeExchange(exchange -> exchange
				.pathMatchers("/api/login", "/api/error").permitAll() // Public routes
				.pathMatchers("/payment/success", "/payment/cancel", "/payment/error").permitAll() // Allow PayPal redirects
				.anyExchange().authenticated() // Secure all other routes
				)
		.oauth2ResourceServer(oauth2 -> oauth2
				.jwt(jwt -> jwt.jwtDecoder(ReactiveJwtDecoders.fromIssuerLocation("https://accounts.google.com"))) // Use reactive JWT decoder
				);

		return http.build();
	}
}
