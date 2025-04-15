package com.example.oauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/api/login", "/api/error").permitAll()
                .requestMatchers("/payment/success", "/payment/cancel", "/payment/error").permitAll()
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth2 -> oauth2
               .defaultSuccessUrl("/api/userinfo", true) // Redirect after successful login
                .failureUrl("/api/error")               // Redirect here on failure
            );

        return http.build();
    }
}
