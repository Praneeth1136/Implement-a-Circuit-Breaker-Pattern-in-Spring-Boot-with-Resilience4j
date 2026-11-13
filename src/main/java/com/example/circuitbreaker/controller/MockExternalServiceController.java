package com.example.circuitbreaker.controller;

import com.example.circuitbreaker.dto.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/external/api")
public class MockExternalServiceController {

    private final AtomicInteger requestCount = new AtomicInteger(0);

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable String id) {
        int count = requestCount.incrementAndGet();

        // Fail 80% of the time to quickly trip the circuit breaker during testing
        if (count % 5 != 0) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.ok(new UserDTO(id, "Real User " + id, "user" + id + "@example.com"));
    }

    @GetMapping("/reset")
    public ResponseEntity<String> resetCount() {
        requestCount.set(0);
        return ResponseEntity.ok("Count reset");
    }
}
