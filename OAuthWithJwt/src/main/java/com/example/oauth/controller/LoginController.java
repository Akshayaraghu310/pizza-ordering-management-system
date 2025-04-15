package com.example.oauth.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

@RestController
public class LoginController {
    private final ClientRegistrationRepository clientRegistrationRepository;
    //private final RestTemplate restTemplate;

    public LoginController(ClientRegistrationRepository clientRegistrationRepository) {
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    // Initiate login process
    @GetMapping("/api/login")
    public Map<String, String> login(HttpServletRequest request) {
        String authorizationRequestUri = OAuth2AuthorizationRequestRedirectFilter.DEFAULT_AUTHORIZATION_REQUEST_BASE_URI
                + "/google";

        Map<String, String> response = new HashMap<>();
        response.put("redirectUri", authorizationRequestUri);

        return response; 
    }

    // Retrieve user info after login and validate Google OAuth token
    @GetMapping("/api/userinfo")
    public Map<String, Object> getUserInfo(
            @RegisteredOAuth2AuthorizedClient("google") OAuth2AuthorizedClient authorizedClient) {

        // Extract the id_token from the authorized client
        String idTokenValue = extractIdToken(authorizedClient);

        // Return the user info along with the id_token
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("idToken", idTokenValue); // Return Google id token
        userInfo.put("accessToken", authorizedClient.getAccessToken().getTokenValue());  
        userInfo.put("refreshToken", authorizedClient.getRefreshToken() != null
                ? authorizedClient.getRefreshToken().getTokenValue()
                : null);

        return userInfo;
    }
    
    private String extractIdToken(OAuth2AuthorizedClient authorizedClient) {
        // Attempt to fetch the id_token from the principal details if available
        OAuth2AuthenticationToken authentication = 
                (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof OidcUser) {
            OidcUser oidcUser = (OidcUser) authentication.getPrincipal();
            return oidcUser.getIdToken().getTokenValue();
        }

        return null; // Return null if id_token is not available
    }
}
