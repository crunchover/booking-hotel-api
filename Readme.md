# Hotel Availability Search API

REST API built with Spring Boot for registering and counting hotel availability searches. Uses an event-driven architecture with Apache Kafka and persists data in PostgreSQL.

---

## Stack

- **Java 21** (virtual threads)
- **Spring Boot 3.4.3**
- **Apache Kafka** (producer + consumer)
- **PostgreSQL 15**
- **Flyway** (schema migrations)
- **SpringDoc OpenAPI / Swagger UI**
- **JaCoCo** (test coverage)
- **Docker Compose** (full containerized setup)

---

## Architecture

The project follows **Hexagonal Architecture** (Ports and Adapters), organized into:

```
com.hotel.booking
├── domain/           # Business models and port interfaces (no framework deps)
├── application/      # Use case implementations
├── infrastructure/   # REST controllers, Kafka, JPA adapters, config
└── shared/           # Exceptions, validators, utils
```

Dependencies flow inward: infrastructure → application → domain. The domain has no knowledge of Spring or Kafka.

---

## Running the application

The only requirement is **Docker** and **Docker Compose**.

```bash
git clone <repo-url>
cd booking-hotel-api
docker compose up --build
```

This will:
1. Compile the application inside Docker (no local Java/Gradle needed)
2. Start PostgreSQL, Zookeeper, Kafka, and the API

Wait for the API to be ready (watch for `Started BookingApiApplication`), then access:

- **API**: `http://localhost:8080`
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`

To stop everything:
```bash
docker compose down -v
```

---

## Endpoints

### POST /search — Register a hotel searh

```bash
curl -X POST http://localhost:8080/search \
  -H "Content-Type: application/json" \
  -d '{
    "hotelId": "1234aBc",
    "checkIn": "29/12/2023",
    "checkOut": "31/12/2023",
    "ages": [30, 29, 1, 3]
  }'
```

**Response** `201 Created`:
```json
{
  "searchId": "gWRsIt-pSjzAyBkLskleT-GRq_knzZdoNt1p1q8HSi4"
}
```

> Note: `ages` order matters. `[30, 29]` and `[29, 30]` produce different `searchId` values.

### GET /count — Get count of identical searches

```bash
curl "http://localhost:8080/count?searchId=gWRsIt-pSjzAyBkLskleT-GRq_knzZdoNt1p1q8HSi4"
```

**Response** `200 OK`:
```json
{
  "searchId": "gWRsIt-pSjzAyBkLskleT-GRq_knzZdoNt1p1q8HSi4",
  "search": {
    "hotelId": "1234aBc",
    "checkIn": "29/12/2023",
    "checkOut": "31/12/2023",
    "ages": [30, 29, 1, 3]
  },
  "count": 3
}
```

---

## Request validation

| Field | Rule |
|---|---|
| `hotelId` | Required, non-blank |
| `checkIn` | Required, format `dd/MM/yyyy`, must be before `checkOut` |
| `checkOut` | Required, format `dd/MM/yyyy` |
| `ages` | Required, at least 1 element, each value between 0 and 120 |

---

## Test coverage report (JaCoCo)

Run tests and generate the coverage report locally (requires Java 21 + Gradle, or use the Docker build):

```bash
./gradlew test jacocoTestReport
```

HTML report available at:
```
build/reports/jacoco/test/html/index.html
```

To verify the 80% threshold:
```bash
./gradlew jacocoTestCoverageVerification
```

---
