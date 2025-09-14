# 🛡️ FraudScope – Scalable Spring Boot Backend for Financial Fraud Detection

**FraudScope** is a scalable and modular Spring Boot backend designed as a foundational framework for financial fraud detection systems.  
It features secure user registration with email verification, 2FA login using OTP tokens, permission-based access control, and a structured role hierarchy.

The user domain supports both `EndUser` and `Admin` roles with isolated endpoints.  
> ⚠️ Fraud detection logic is **not implemented yet**, but the system is built with extensibility and modularity in mind.

---

## 🚀 Features

### ✅ User Registration with Email Verification
- Email verification required before account activation  
- Verification tokens securely generated, stored, and validated  

### 🔐 2FA Login with OTP Verification
- After login, users must enter a one-time password (OTP) sent to their email  
- Stateless verification using signed OTP tokens (JWT)  

### 🧾 Permission-Based Access Control (RBAC)
- Dynamic permissions enforced via `@PreAuthorize("hasAuthority(...)")`  
- Permissions defined in `PredefinedPermission` enum  
- Mapped to roles via `RolePermission`  
- Users linked to roles via `RoleUser`  

### 🧑‍💼 EndUser & Admin Separation
- Abstract `User` entity extended by `EndUser` and `Admin`  
- Separated services, controllers, and permission scopes  

### 🛠️ Seeder System for Initial Role & Permission Setup
- Roles & permissions seeded from enums on startup  
- `systemAuthUser` created automatically and granted all permissions  
- Executed with `@Ordered` `CommandLineRunner`  

### 🌀 AOP-Based Exception Logging
- Service-layer exceptions auto-logged with:
  - Class, method  
  - HTTP method, path, IP, User-Agent  
  - Exception type, message, timestamp  
- All captured via a custom `ExceptionLogContext` model  

### ⏱️ Scheduled Cleanup for OTP & Refresh Tokens
- Scheduled tasks:
  - Mark expired OTPs as `EXPIRED`  
  - Permanently delete old tokens  
  - Revoke expired refresh tokens  
  - Hard-delete revoked tokens after X days  

### 📡 Redis-Based Rate Limiting & Caching
- Rate limiting is applied to **Login, Register, and OTP verification** endpoints  
- Redis provides **stateless** and high-performance request limiting  
- **User/IP-based dynamic key generation** implemented via `RateLimitKeyGenerator`  
- When the Login endpoint exceeds its limit, a **Kafka event** is triggered and the user is notified via email  

### 📬 Kafka-Driven Notification System
- Email delivery is fully **asynchronous**:  
  * `Producer → Kafka Topic → Consumer → Dispatcher → SMTP`  
- **Dead Letter Queue (DLQ)** support ensures failed messages are safely redirected  
- **Retry mechanism** with configurable backoff is applied  
- On Redis rate limit breaches, a **Kafka-triggered email notification** is sent to the user  

### ⚙️ Centralized Error Handling
- Global exception handler with custom `BaseException` model  
- Structured responses: `ApiError<T>`  
- Metadata included: `timestamp`, `path`, `status`, `i18nKey` support  


---

## 🧪 Unit Testing – Authentication Flow

> **Coverage:** `34.7%` (and growing)

Unit tests written for three main services:

### ✅ `AuthServiceImpl`
- Register success & failure  
- Login success, invalid password, 2FA checks  
- Disabled or unverified user rejection  

### ✅ `VerificationTokenServiceImpl`
- Email verification flows  
- Expired, invalid, already-used token handling  

### ✅ `OtpTokenServiceImpl`
- Valid OTP entry  
- Expired, blocked, or over-limit scenarios  

Test data generated via `AuthTestDataFactory`, using `Mockito` for mocks.

---

## 🧱 Tech Stack

| Layer        | Technologies                                      |
|--------------|---------------------------------------------------|
| Backend      | Java 17, Spring Boot 3.2.5                        |
| API Docs     | Springdoc OpenAPI, Swagger UI                     |
| Persistence  | Spring Data JPA, Hibernate, MySQL, Redis          |
| Security     | Spring Security, JWT, BCrypt, 2FA (OTP), Rate Limiter |
| Messaging    | Apache Kafka, Kafka DLQ, DeadLetterPublishingRecoverer |
| Infra        | Scheduled Tasks, CommandLineRunner Seeder         |
| Dev Tools    | Lombok, MapStruct, Maven                          |
| Testing      | JUnit 5, Mockito                                  |

---

## 📄 API Documentation

- 🔗 Swagger UI → [`/swagger-ui/index.html`](http://localhost:8080/swagger-ui/index.html)  
- 🔗 OpenAPI Spec → [`/v3/api-docs`](http://localhost:8080/v3/api-docs)  

---

## 📬 Postman Collection

The project includes a ready-to-use Postman collection:  
🌐 [FraudScope Public Postman Link](https://www.postman.com/lunar-module-operator-48760766/springbootprojects/collection/wxc67nh/fraudscope)

---

## 📡 Sample API Endpoints

| Method | Endpoint                                | Description                                       |
|--------|-----------------------------------------|---------------------------------------------------|
| POST   | `/api/v1/auth/register`                 | Register new end-user (with email verification)   |
| POST   | `/api/v1/auth/login/verify-otp`         | Verify OTP for 2FA login                          |
| POST   | `/api/v1/auth/refresh-token/rotate`     | Rotate access & refresh tokens                    |
| GET    | `/api/v1/end-user/profile/details`      | Fetch end-user profile                            |
| PUT    | `/api/v1/end-user/profile/update`       | Update profile information                        |
| DELETE | `/api/v1/end-user/profile/delete`       | Soft delete end-user account                      |

> 🔐 All endpoints require `Authorization: Bearer <access_token>` header after login & OTP verification.

---

## 🧱 Clean & Modular Architecture

- Layered structure → `Controller → Service → Repository`  
- Soft deletes and auditing included  
- DTOs, mappers, and centralized validation & exception layers  
- JPA relationships with `Lazy` fetching  

---

## 🧭 Upcoming Features

### 📦 Domain Endpoints
- Account management endpoints  
- Transaction endpoints  
- Role & Permission endpoints  
- RoleUser & RolePermission mapping endpoints  

### 📊 ElasticSearch & Pagination
- Full-text search for notes & transactions using ElasticSearch  
- Pagination, sorting, and filtering for large datasets  

---

## ⚙️ Setup Instructions

```bash
# 1. Clone the project
git clone https://github.com/alirizakaygusuz/fraudscope-backend.git
cd fraudscope-backend

# 2. Update database config in src/main/resources/application.yml

# 3. Build and run
mvn clean install
mvn spring-boot:run 
```

---

## ✍️ Author

**Ali Rıza Kaygusuz** – 🛠️ Java Backend Developer  
🌐 [GitHub Profile](https://github.com/alirizakaygusuz)  
💼 [LinkedIn Profile](https://www.linkedin.com/in/alirizakaygusuz)

---

## 📄 License

This project is licensed under the MIT License.  
📃 [MIT License Link](https://opensource.org/licenses/MIT)

> Feel free to fork, contribute, or use it freely in your own applications.
