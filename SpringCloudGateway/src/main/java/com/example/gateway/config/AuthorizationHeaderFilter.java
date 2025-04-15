package com.example.gateway.config;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    public AuthorizationHeaderFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            // Retrieve the id_token (which is a JWT) from the SecurityContext
            String token = getIdTokenFromSessionOrSecurityContext(exchange);

            if (token != null) {
                // Mutate the request to add the Authorization header dynamically with the id_token (JWT)
                exchange.getRequest().mutate()
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .build();
            }

            // Continue with the filter chain
            return chain.filter(exchange);
        };
    }

    private String getIdTokenFromSessionOrSecurityContext(ServerWebExchange exchange) {
        // Access SecurityContext to get the Authentication object
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;

            // Retrieve the principal (OidcUser) from the Authentication token
            if (authToken.getPrincipal() instanceof OidcUser) {
                OidcUser oidcUser = (OidcUser) authToken.getPrincipal();
                // Extract the id_token from the OidcUser and return it
                return oidcUser.getIdToken().getTokenValue(); // This is the actual id_token (JWT)
            }
        }
        return null; // Return null if no token is found
    }

    public static class Config {
        
    }
}
