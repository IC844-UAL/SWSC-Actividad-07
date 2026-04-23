# Microservices with Eureka

This project has 3 Spring Boot services that work together using **Eureka Service Discovery**:

- `Eureka Server` (port `8761`)
- `check-movie-service` Version A (port `8081`)
- `check-movie-service-b` Version B (port `8082`)

## What each service does

### 1) Eureka Server

- Runs as the service registry.
- All microservices register themselves here.
- Eureka dashboard: `http://localhost:8761`

### 2) check-movie-service (Version A)

- `spring.application.name=check-movie-service`
- Uses OMDb API to check if a movie exists.
- Endpoint:
  - `GET /check?title=...`
  - Example: `http://localhost:8081/check?title=Inception`
- Returns:
  - `Movie exists (Version A)`
  - `Movie not found (Version A)`

### 3) check-movie-service-b (Version B)

- `spring.application.name=check-movie-service` (same logical service name)
- Uses TMDb API to check if a movie exists.
- Endpoint:
  - `GET /check?title=...`
  - Example: `http://localhost:8082/check?title=Inception`
- Returns:
  - `Movie exists (Version B)`
  - `Movie not found (Version B)`

## How they work together with Eureka

1. Start `Eureka Server` first.
2. Start Version A and Version B.
3. Both movie services register in Eureka under the same app name:
   - `CHECK-MOVIE-SERVICE`
4. Eureka then knows there are multiple instances of the same logical service on different ports (`8081`, `8082`).

In this setup, clients can discover service instances through Eureka instead of hardcoding a single host/port.

## API keys

Set your keys in each service:

- `check-movie-service/src/main/resources/application.properties`
  - `omdb.api.key=YOUR_KEY`
- `check-movie-service-b/src/main/resources/application.properties`
  - `tmdb.api.key=YOUR_KEY`

## Run commands

From each folder:

- Eureka:
  - `mvn spring-boot:run`
- Version A:
  - `mvn spring-boot:run`
- Version B:
  - `mvn spring-boot:run`

## Quick verification

- Eureka dashboard: `http://localhost:8761`
- Version A check: `http://localhost:8081/check?title=Inception`
- Version B check: `http://localhost:8082/check?title=Inception`

