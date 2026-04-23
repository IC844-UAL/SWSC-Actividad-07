# Microservices with Eureka and OpenFeign

This project has 4 Spring Boot services that work together using **Eureka Service Discovery** and **OpenFeign**:

- `Eureka Server` (port `8761`)
- `check-movie-service` Version A (port `8081`)
- `check-movie-service-b` Version B (port `8082`)
- `manage-movies` consumer service (port `8080`)

## Architecture overview

1. `Eureka Server` is the registry where services register.
2. Version A and Version B both register with the same service name:
   - `check-movie-service`
3. `manage-movies` calls `check-movie-service` through **OpenFeign** using the service name (not a fixed host/port).
4. Eureka + load balancing resolve which instance receives the request (Version A or Version B).

Because A and B share the same `spring.application.name`, requests from `manage-movies` may hit either instance.

## Services

### 1) Eureka Server

- Role: service registry
- URL: `http://localhost:8761`

### 2) check-movie-service (Version A)

- App name: `check-movie-service`
- Port: `8081`
- External API: OMDb
- Endpoint:
  - `GET /check?title=...`
  - Example: `http://localhost:8081/check?title=Inception`
- Responses:
  - `Movie exists (Version A)`
  - `Movie not found (Version A)`

### 3) check-movie-service-b (Version B)

- App name: `check-movie-service`
- Port: `8082`
- External API: TMDb
- Endpoint:
  - `GET /check?title=...`
  - Example: `http://localhost:8082/check?title=Inception`
- Responses:
  - `Movie exists (Version B)`
  - `Movie not found (Version B)`

### 4) manage-movies (consumer)

- App name: `manage-movies`
- Port: `8080`
- Uses: Eureka Discovery Client + OpenFeign
- Feign target: `@FeignClient(name = "check-movie-service")`
- Endpoint:
  - `GET /insert?title=...`
  - Example: `http://localhost:8080/insert?title=Inception`
- Responses:
  - `Movie validated. Ready to insert into Database. Response: ...`
  - `Cannot insert: Movie not found`

## API keys

Set your keys in:

- `check-movie-service/src/main/resources/application.properties`
  - `omdb.api.key=YOUR_KEY`
- `check-movie-service-b/src/main/resources/application.properties`
  - `tmdb.api.key=YOUR_KEY`

## Run order

1. Start `Eureka Server`
2. Start `check-movie-service` (Version A)
3. Start `check-movie-service-b` (Version B)
4. Start `manage-movies`

Run in each service folder:

- `mvn spring-boot:run`

## Quick verification

- Eureka dashboard: `http://localhost:8761`
- Version A direct check: `http://localhost:8081/check?title=Inception`
- Version B direct check: `http://localhost:8082/check?title=Inception`
- Through Eureka + Feign (consumer): `http://localhost:8080/insert?title=Inception`

Expected consumer behavior:

- If movie exists in selected instance response:
  - `Movie validated. Ready to insert into Database. Response: Movie exists (Version A|Version B)`
- Otherwise:
  - `Cannot insert: Movie not found`

