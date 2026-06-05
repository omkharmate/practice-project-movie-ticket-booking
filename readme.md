# Movie Ticket Booking System - Microservices Project

## Overview
This is a backend microservices-based Movie Ticket Booking System being built using Spring Boot and Docker.

The goal of this project is to understand scalable backend architecture and core microservice concepts by implementing a simplified real-world system.

---

## Tech Stack

- Java 21
- Spring Boot
- Spring Data JPA
- MySQL
- Docker
- REST APIs
- Maven

---

## Overview

Built a production-style backend using Spring Boot microservices with 3 independent services — movie-service, theatre-service, and booking-service. Each service has its own MySQL database. Docker Compose is used to run all services along with Redis, Prometheus, and Grafana.

---

## Week 1 — Backend Basics (May 05–08)

Java 21 — used across all three services. Leveraged Lombok builders, enums like BookingStatus and SeatLockStatus, and modern Java features.

JSON — Jackson ObjectMapper configured in RedisConfig. All API communication uses JSON request and response DTOs.

HTTP and REST — MovieController, BookingController, ShowController all follow REST conventions with proper GET, POST, PUT, DELETE usage and resource-based URLs like /movies/{id} and /shows/{id}.

HTTP Status Codes — GlobalExceptionHandler returns 404 for not found, 409 for seat conflicts, and 503 when theatre-service is down. Each error has a structured ErrorResponse body with timestamp and path.

Swagger — springdoc-openapi added as a dependency. Swagger UI available at /swagger-ui.html in booking-service.

---

## Week 2 — Spring Ecosystem (May 11–15)

Spring Boot — 3 separate Spring Boot applications, each running on its own port (8081, 8082, 8083).

Spring Data JPA — JpaRepository used on all entities. Custom JPQL and native SQL queries written using @Query. Soft delete and bulk update done with @Modifying to avoid unnecessary SELECT calls. Pagination implemented using PageRequest.

Spring Cloud Feign — TheatreClient uses @FeignClient to call /shows/{id} and /seats/{id} on theatre-service from within booking-service.

Spring Cloud Circuit Breaker — Resilience4j wired via spring.cloud.openfeign.circuitbreaker.enabled=true. FallbackFactory implemented with cause logging. Configured with 50% failure threshold, 10 second open state, and half-open probing.

Spring Security — not implemented yet. Planned as part of the upcoming User Service.

---

## Week 3 — Dev Tools and Build (May 18–22)

Maven — each service has its own pom.xml with full dependency management. Maven wrapper included. JARs built successfully: booking-service-0.0.1-SNAPSHOT.jar and movie-service-0.0.1-SNAPSHOT.jar.

Git — 16 commits on main branch. Created feature/redisimpl branch for Redis work, rebased it onto main, and merged using the ort strategy. CODEOWNERS file added.

IntelliJ — .idea workspace present with compiler config, inspection profiles, and HTTP request logs saved in httpRequests folder.

Jenkins — not implemented yet. Listed as a planned addition.

---

## Week 4 — Databases and Search (May 25–29)

MySQL — 3 separate MySQL 8.0 containers running via Docker Compose for movie_db, theatre_db, and booking_db. data.sql used for seeding. JPA relations and custom queries used throughout.

Redis — implemented on a dedicated feature branch and merged to main. RedisCacheManager configured with a 10-minute TTL. Cacheable, CachePut, and CacheEvict annotations applied on all movie APIs. TTL is configurable via application.properties.

Search — MySQL FULLTEXT search implemented in MovieRepository using MATCH/AGAINST in BOOLEAN MODE for keywords of 3 or more characters. Falls back to LIKE for shorter inputs.

MongoDB and ScalaDB — not used. MySQL was chosen as the primary database for all three services.

---

## Week 5 — Infra, Monitoring and Concepts (Jun 01–05)

Docker — docker-compose.yml runs Redis, 3 MySQL instances, Prometheus, and Grafana. Named volumes and a dedicated monitoring network configured.

Prometheus — prometheus.yml scrapes all 3 services at /actuator/prometheus every 15 seconds. Enabled in each service via management.endpoint.prometheus.enabled=true.

Grafana — running on port 3000 via Docker Compose, connected to Prometheus as a data source.

Performance metrics — Spring Boot Actuator exposes all endpoints. Circuit breaker health indicators exposed. management.health.show-details=always set across services.

AWS — not deployed yet. Listed as an optional step in the mini project plan.

---

## Additional

Seat locking with scheduler — SeatLockScheduler runs every 30 seconds and automatically expires seats that were locked but not confirmed. This prevents race conditions when multiple users try to book the same seat at the same time.

Soft delete — movies are deactivated using a single UPDATE query that sets active to false, without loading the entity first.

Pagination — getAllMovies uses PageRequest to avoid loading all records into memory. Cache key includes page and size so each page is cached separately.
---

## What is Still Pending

Spring Security and user authentication — planned for the next phase along with a User Service.

Jenkins CI pipeline — planned.

AWS deployment — optional, listed in the mini project plan.

MongoDB and ScalaDB — not in scope for this project.

 
