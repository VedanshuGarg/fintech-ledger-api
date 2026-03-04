# FinTech Ledger API

A high-performance, double-entry ledger service built with **Java 17**, **Spring Boot 3**, and **PostgreSQL**. designed to handle high-concurrency financial transactions with audit trails.

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2-green)
![Docker](https://img.shields.io/badge/Docker-Enabled-blue)
![Postgres](https://img.shields.io/badge/Postgres-15-blue)

## Key Features
- **Precision Math:** Uses `BigDecimal` for all monetary calculations to prevent floating-point errors.
- **Concurrency Safe:** Atomic transactions using `@Transactional` to ensure data integrity.
- **Audit Ready:** Immutable transaction logs with timestamps and reference IDs.
- **Dockerized:** One-command setup with `docker-compose`.

## Architecture
- **Controller:** REST API Layer (`/v1/transactions`)
- **Service:** Business Logic (Overdraft protection, Balance calculation)
- **Repository:** JPA/Hibernate for SQL abstraction
- **Database:** PostgreSQL 15 (Containerized)

## Quick Start
1. **Clone the repo:**
   ```bash
   git clone [https://github.com/VedanshuGarg/fintech-ledger-api.git](https://github.com/VedanshuGarg/fintech-ledger-api.git)

2. **Start the Database (Docker):**
    ```bash
    docker-compose up -d
    
3. **Run the Application:**
    ```bash
    ./mvnw spring-boot:run
    
API Endpoints

1. Create a Transaction (Credit/Debit)
Endpoint: POST /v1/transactions

Request (Deposit Money):
```bash
curl -X POST http://localhost:8080/api/transactions \
-H "Content-Type: application/json" \
-d '{
  "accountNumber": "ACC-1001",
  "amount": 5000.00,
  "type": "CREDIT"
}'
```

Request (Withdraw Money):
```bash
curl -X POST http://localhost:8080/api/transactions \
-H "Content-Type: application/json" \
-d '{
  "accountNumber": "ACC-1001",
  "amount": 1500.50,
  "type": "DEBIT"
}'
```

2. Get Account Balance
Endpoint: GET /api/transactions/{accountNumber}/balance

Request:
```bash
curl -X GET http://localhost:8080/api/transactions/ACC-1001/balance
```
Response: 
```bash
3499.50
```

📂 Project Structure
```bash
src/main/java/com/vedanshu/fintech
├── controller/
│   └── TransactionController.java    # REST Endpoints
├── dto/
│   └── TransactionRequest.java       # Java 17 Record for API Payload
├── model/
│   ├── Transaction.java              # JPA Entity
│   └── TransactionType.java          # Enum (CREDIT, DEBIT)
├── repository/
│   └── TransactionRepository.java    # Spring Data JPA Interface
└── service/
    └── TransactionService.java       # Core Business Logic & Math
```

Built by Vedanshu Garg | Associate Software Engineer @ CloudSufi
