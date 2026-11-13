package com.example.circuitbreaker.controller;

import com.example.circuitbreaker.dto.UserDTO;
import com.example.circuitbreaker.service.UserDataService;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserDataService userDataService;
    private final CircuitBreakerRegistry circuitBreakerRegistry;

    public UserController(UserDataService userDataService, CircuitBreakerRegistry circuitBreakerRegistry) {
        this.userDataService = userDataService;
        this.circuitBreakerRegistry = circuitBreakerRegistry;
    }

    @GetMapping("/users/{id}")
    public UserDTO getUser(@PathVariable String id) {
        return userDataService.fetchUser(id);
    }

    @GetMapping("/circuit-breaker/state")
    public String getCircuitBreakerState() {
        return circuitBreakerRegistry.circuitBreaker("userService").getState().toString();
    }

    @GetMapping("/circuit-breaker/metrics")
    public Map<String, Object> getCircuitBreakerMetrics() {
        CircuitBreaker cb = circuitBreakerRegistry.circuitBreaker("userService");
        CircuitBreaker.Metrics metrics = cb.getMetrics();

        Map<String, Object> metricsMap = new HashMap<>();
        metricsMap.put("failureRate", metrics.getFailureRate());
        metricsMap.put("numberOfBufferedCalls", metrics.getNumberOfBufferedCalls());
        metricsMap.put("numberOfFailedCalls", metrics.getNumberOfFailedCalls());
        metricsMap.put("numberOfSuccessfulCalls", metrics.getNumberOfSuccessfulCalls());
        return metricsMap;
    }
}
