# Backend Technical Exercise

Implementación de una arquitectura de microservicios con Spring Boot, compuesta por 2 servicios independientes que se comunican de forma asincrónica mediante RabbitMQ.

---

## Arquitectura

```
ms-clients  →  Gestión de Personas y Clientes
ms-accounts →  Gestión de Cuentas y Movimientos
```

La comunicación entre microservicios se realiza de forma **asincrónica** mediante **RabbitMQ**.

---

## Stack tecnológico

| Tecnología | Uso |
|---|---|
| Spring Boot 3.x | Framework principal |
| PostgreSQL | Base de datos por MS |
| RabbitMQ | Comunicación asincrónica |
| Zipkin | Trazabilidad distribuida |
| Grafana + Loki | Logs centralizados |
| Resilience4j | Circuit Breaker |
| Spring Retry | Reintentos automáticos |
| Mockito | Pruebas unitarias |
| Karate | Pruebas de integración |
| Docker | Contenedorización |

---

## Requisitos previos

- Java 17
- Docker Desktop
- IntelliJ IDEA o VS Code

---

## Levantar la infraestructura

```bash
docker-compose up -d
```

Esto levanta:

| Servicio | Puerto |
|---|---|
| PostgreSQL ms-clients | 5432 |
| PostgreSQL ms-accounts | 5433 |
| RabbitMQ | 5672 |
| RabbitMQ Management | 15672 |
| Zipkin | 9411 |
| Loki | 3100 |
| Grafana | 3000 |

---

## Levantar los microservicios

```bash
# ms-clients
cd ms-clients
./gradlew bootRun

# ms-accounts
cd ms-accounts
./gradlew bootRun
```

---

## Endpoints disponibles

### ms-clients (puerto 8080)

| Método | Endpoint | Descripción |
|---|---|---|
| GET | /clientes | Listar todos los clientes |
| GET | /clientes/{id} | Obtener cliente por id |
| POST | /clientes | Crear cliente |
| PUT | /clientes/{id} | Actualizar cliente |
| DELETE | /clientes/{id} | Eliminar cliente |

### ms-accounts (puerto 8081)

| Método | Endpoint | Descripción |
|---|---|---|
| GET | /cuentas | Listar todas las cuentas |
| GET | /cuentas/{id} | Obtener cuenta por id |
| POST | /cuentas | Crear cuenta |
| PUT | /cuentas/{id} | Actualizar cuenta |
| DELETE | /cuentas/{id} | Eliminar cuenta |
| GET | /movimientos | Listar todos los movimientos |
| GET | /movimientos/{id} | Obtener movimiento por id |
| POST | /movimientos | Registrar movimiento |
| PUT | /movimientos/{id} | Actualizar movimiento |
| DELETE | /movimientos/{id} | Eliminar movimiento |
| GET | /reportes | Reporte de estado de cuenta |

---

## Ejemplos de uso

### Crear un cliente

```bash
curl -X POST http://localhost:8080/clientes \
-H "Content-Type: application/json" \
-d '{
  "name": "Juan Perez",
  "gender": "Masculino",
  "age": 30,
  "identification": "1234567890",
  "address": "Calle 123",
  "phone": "0987654321",
  "password": "password123",
  "status": true
}'
```

### Crear una cuenta

```bash
curl -X POST http://localhost:8081/cuentas \
-H "Content-Type: application/json" \
-d '{
  "accountNumber": "478758",
  "accountType": "Ahorro",
  "initialBalance": 1000.00,
  "status": true,
  "clientId": 1
}'
```

### Registrar un movimiento

```bash
curl -X POST http://localhost:8081/movimientos \
-H "Content-Type: application/json" \
-d '{
  "movementType": "Retiro",
  "value": -100.00,
  "accountId": 1
}'
```

### Reporte de estado de cuenta

```bash
curl "http://localhost:8081/reportes?clientId=1&startDate=2024-01-01&endDate=2024-12-31"
```

---

## Comunicación asincrónica

Cuando se **crea** o **elimina** un cliente en ms-clients, se publica un evento en RabbitMQ:

```
cliente.creado    →  ms-accounts crea una cuenta automáticamente
cliente.eliminado →  ms-accounts desactiva las cuentas del cliente
```

---

## Resiliencia

ms-accounts implementa **Circuit Breaker** y **Retry** al consultar ms-clients:

```
Retry           →  3 reintentos con 2s de espera
Circuit Breaker →  se abre si 50% de las llamadas fallan
Fallback        →  devuelve datos por defecto si todo falla
```

---

## Observabilidad

| Herramienta | URL | Credenciales |
|---|---|---|
| RabbitMQ Management | http://localhost:15672 | guest / guest |
| Zipkin | http://localhost:9411 | - |
| Grafana | http://localhost:3000 | admin / admin |

---

## Pruebas

### Unitarias (Mockito)

```bash
cd ms-clients
./gradlew test
```

### Integración (Karate)

```bash
cd ms-accounts
./gradlew test
```

---

## Estructura del proyecto

```
backend-technical-exercise/
├── ms-clients/
│   └── src/main/java/com/matiasnuniez/msclients/
│       ├── client/
│       ├── config/
│       ├── controller/
│       ├── dto/
│       ├── exception/
│       ├── mapper/
│       ├── messaging/
│       ├── model/
│       ├── repository/
│       └── service/
├── ms-accounts/
│   └── src/main/java/com/matiasnuniez/ms_accounts/
│       ├── client/
│       ├── config/
│       ├── controller/
│       ├── dto/
│       ├── exception/
│       ├── mapper/
│       ├── messaging/
│       ├── model/
│       ├── repository/
│       └── service/
└── docker-compose.yml
```