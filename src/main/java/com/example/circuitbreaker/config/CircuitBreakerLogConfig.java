package com.example.circuitbreaker.config;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

@Configuration
public class CircuitBreakerLogConfig {

    private final CircuitBreakerRegistry circuitBreakerRegistry;

    public CircuitBreakerLogConfig(CircuitBreakerRegistry circuitBreakerRegistry) {
        this.circuitBreakerRegistry = circuitBreakerRegistry;
    }

    @PostConstruct
    public void setupLoggers() {
        CircuitBreaker cb = circuitBreakerRegistry.circuitBreaker("userService");
        cb.getEventPublisher()
            .onStateTransition(event -> {
                System.out.println("CircuitBreaker '" + event.getCircuitBreakerName() +
                                   "' changed state from " + event.getStateTransition().getFromState() +
                                   " to " + event.getStateTransition().getToState());
            });
    }
}
