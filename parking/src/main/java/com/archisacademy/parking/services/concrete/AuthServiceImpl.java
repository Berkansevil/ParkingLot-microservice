package com.archisacademy.parking.services.concrete;


import com.archisacademy.parking.dtos.request.UserAuthRequest;
import com.archisacademy.parking.dtos.request.UserLoginRequest;
import com.archisacademy.parking.dtos.response.UserAuthResponse;
import com.archisacademy.parking.services.abstracts.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    private final Logger logger= LoggerFactory.getLogger(AuthServiceImpl.class);
    private final RestTemplate restTemplate;
    @Value("${keycloak.admin-url}")
    private String keycloakAdminUrl;

    @Value("${keycloak.admin-client-id}")
    private String adminClientId;

    @Value("${keycloak.token-url}")
    private String keycloakTokenUrl;

    @Value("${keycloak.app-client-id}")
    private String appClientId;

    @Value("${keycloak.client-secret}")
    private String clientSecret;

    @Value("${keycloak.admin-client-secret}")
    private String adminClientSecret;

    @Value("${keycloak.logout-url}")
    private String keycloakLogoutUrl;

    public AuthServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseEntity<UserAuthResponse> registerUser(UserAuthRequest userAuthRequest) {
        String adminToken = getAdminAccessToken();
        if (adminToken == null) {
            throw new RuntimeException("Failed to obtain admin access token");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(adminToken);
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("username", userAuthRequest.getUsername());
        userMap.put("email", userAuthRequest.getEmail());
        userMap.put("enabled", true);
        userMap.put("credentials", List.of(Map.of("type", "password", "value", userAuthRequest.getPassword(), "temporary", false)));
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(userMap, headers);
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(keycloakAdminUrl, request, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.ok(new UserAuthResponse("User registered successfully", true));
            } else {
                return ResponseEntity.status(response.getStatusCode())
                        .body(new UserAuthResponse("Failed to register user: " + response.getBody(), false));
            }
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .body(new UserAuthResponse("Error registering user: " + e.getResponseBodyAsString(), false));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new UserAuthResponse("Unexpected error: " + e.getMessage(), false));
        }

    }

    @Override
    public ResponseEntity<Map<String, Object>> loginUser( UserLoginRequest userLoginRequest) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", appClientId);
        body.add("client_secret", clientSecret);
        body.add("username", userLoginRequest.getUsername());
        body.add("password", userLoginRequest.getPassword());
        body.add("grant_type", "client_credentials");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);


        try {
            ResponseEntity<Map<String, Object>> response =
                    restTemplate.exchange(keycloakTokenUrl, HttpMethod.POST, request,
                            new ParameterizedTypeReference<Map<String, Object>>() {});

            if (response.getStatusCode().is2xxSuccessful()) {
                return response;
            } else {
                String errorMessage = "Login failed: " + response.getStatusCode() + " - " + response.getBody();
                logger.error(errorMessage);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", errorMessage));
            }
        } catch (Exception e) {
            logger.error("Error during login: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Login failed"));
        }

    }

    private String getAdminAccessToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", adminClientId);
        body.add("client_secret", adminClientSecret);
        body.add("grant_type", "client_credentials");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(keycloakTokenUrl, request, Map.class);
            return response.getBody() != null ? (String) response.getBody().get("access_token") : null;
        } catch (Exception e) {
            return null;
        }
    }

    public void logout(String accessToken) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + accessToken);

            String formData = "client_id=parking-app-client&redirect_uri=http://localhost:8080/login";

            HttpEntity<String> request = new HttpEntity<>(formData, headers);

            ResponseEntity<String> response = restTemplate.exchange(keycloakLogoutUrl, HttpMethod.POST, request, String.class);

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Error logging out from Keycloak: " + response.getBody());
            }
        } catch (Exception e) {
            System.err.println("Error during Keycloak logout: " + e.getMessage());
            throw new RuntimeException("Logout failed: " + e.getMessage());
        }
    }

}
