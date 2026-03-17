# 🏨 Hotel Search API

Servicio REST para registrar y contar búsquedas de hoteles. Utiliza:

- Spring Boot 3
- PostgreSQL 15
- Apache Kafka (publicador + listener)
- Docker Compose
- Arquitectura simple propone organizar el código fuente en los siguientes paquetes:

* configuration: Clases de configuracion de spring y demas frameworks
* usecase: Interfaces e implementaciones de casos de uso que nuestra solucion ha de resolver
* model: Clases tipo Request / Response / DTOs / Mapeos de hibernate
* validation: Clases que ejecutan validaciones de negocio / validaciones del modelo
    - Se nombran con prefijo Validate o sufijo Validator
* util: clases tipo helpers con funcionalidades tecnicas "cross" que no se ajustan a ningun otro paquete
* gateway: componentes que salen "afuera" del sistema para obtener datos
    - Tienen correspondencia 1 a 1 con cada entidad de negocio
* endpoint: contrapartida de gateway / punto de entrada de la aplicacion
    - Cada punto de entrada debe estar contenido en clases separadas
    - Entradas REST deben nombrarse con sufijo *Endpoint
* exception: contiene excepciones propias de la solucion
---

## 🛠️ Requisitos

- Docker y Docker Compose instalados
- Java 21 (para desarrollo)
- Gradle o IDE compatible con Spring Boot

---

## 🚀 Cómo levantar el proyecto localmente

### 1. Clona el repositorio

```bash
docker compose up --build
```

##  Endpoints

### 1. Realiza una búsqueda de hotel
curl --location 'http://localhost:8080/booking/search' \
--header 'Content-Type: application/json' \
--data '{
  "hotelId": "1234aBc",
  "checkIn": "29/12/2023",
  "checkOut": "31/12/2023",
  "ages": [1, 30, 29, 3]
}'

### Response:

json
{
  "searchId": "gWRsIt-pSjzAyBkLskleT-GRq_knzZdoNt1p1q8HSi4"
}

### 2.Consulta cuántas veces se buscó un hotel (por searchId)
curl --location 'http://localhost:8080/booking/count?searchId=gWRsIt-pSjzAyBkLskleT-GRq_knzZdoNt1p1q8HSi4'

### Response:
{
  "searchId": "1",
  "search": {
    "hotelId": "1234aBc",
    "checkIn": "29/12/2023",
    "checkOut": "31/12/2023",
    "ages": [1, 30, 29, 3]
  },
  "count": 2
}
