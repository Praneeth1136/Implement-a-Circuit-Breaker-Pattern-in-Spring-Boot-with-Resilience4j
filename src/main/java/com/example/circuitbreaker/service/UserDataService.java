package com.example.circuitbreaker.service;

import com.example.circuitbreaker.dto.UserDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;

@Service
public class UserDataService {

    private final RestTemplate restTemplate;

    public UserDataService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @CircuitBreaker(name = "userService", fallbackMethod = "getFallbackUserData")
    public UserDTO fetchUser(String id) {
        String url = "http://localhost:8080/external/api/users/" + id;
        return restTemplate.getForObject(url, UserDTO.class);
    }

    public UserDTO getFallbackUserData(String id, Throwable throwable) {
        System.out.println("Fallback called for user " + id + " due to: " + throwable.getMessage());
        return new UserDTO("default-id", "Default User", "default@example.com");
    }
}
