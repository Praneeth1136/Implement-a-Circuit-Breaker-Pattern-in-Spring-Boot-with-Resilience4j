# Spring Boot Circuit Breaker with Resilience4j

Production-ready implementation of the **Circuit Breaker Pattern** using Spring Boot 3 and Resilience4j, providing automatic fault isolation and fallback degradation for unreliable external APIs.

---

## State Transition Cycle

```
       [ CLOSED ] (Normal Operation)
           │
           │ Failure rate threshold exceeded (>50%)
           ▼
        [ OPEN ] (Requests fail immediately to fallback)
           │
           │ Wait duration expires (e.g., 5 seconds)
           ▼
     [ HALF_OPEN ] (Permits test requests)
        ├── Success rate meets threshold ──► [ CLOSED ]
        └── Failure occurs again ──────────► [ OPEN ]
```

## Resilience4j Configuration Highlights
- **Sliding Window Size:** 10 requests evaluated.
- **Failure Rate Threshold:** 50%.
- **Wait Duration in Open State:** 5000 ms.
- **Permitted Number of Calls in Half-Open State:** 3 calls.

## Testing Circuit Breaker
Execute the included automated test harness:
```bash
chmod +x test-circuit-breaker.sh
./test-circuit-breaker.sh
```
