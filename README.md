# Circuit Breaker Pattern in Spring Boot with Resilience4j

This project demonstrates how to build a fault-tolerant Spring Boot microservice using the Circuit Breaker pattern with Resilience4j.

## Build Instructions
To build the project, run:
```bash
mvn clean install
```

## Run Instructions
To run the application, use the Spring Boot Maven plugin:
```bash
mvn spring-boot:run
```
Or run the JAR file after building:
```bash
java -jar target/circuitbreaker-demo-0.0.1-SNAPSHOT.jar
```

## Testing Instructions
We provide a shell script to demonstrate the circuit breaker functionality.

1. Start the application (see Run Instructions).
2. Make the script executable:
   ```bash
   chmod +x test-circuit-breaker.sh
   ```
3. Run the script:
   ```bash
   ./test-circuit-breaker.sh
   ```

### What to expect
The script will:
- Check the initial state of the circuit breaker (should be `CLOSED`).
- Send multiple requests to an endpoint that fails frequently. This will trip the circuit breaker.
- Check the state (should be `OPEN`).
- Wait for 11 seconds (the wait duration is configured to 10s).
- Check the state (should be `HALF_OPEN`).
- Make a successful request, causing the circuit breaker to close.
- Check the state (should be `CLOSED`).
- You will also see log statements in the application console showing the state transitions.

## Endpoints
- `GET /api/users/{id}`: Fetches user data, falls back if the circuit breaker is open.
- `GET /api/circuit-breaker/state`: Returns the current state of the circuit breaker.
- `GET /api/circuit-breaker/metrics`: Returns metrics for the circuit breaker.
- `GET /actuator/circuitbreakers`: Actuator endpoint for all circuit breaker details.
