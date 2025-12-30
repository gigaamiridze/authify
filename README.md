# Authify

Authify is a secure authentication and user management service that exposes a clear, permission-secured REST interface 
for user authentication, account lifecycle operations, profile access, system configuration, and controlled 
administrative actions.

---

## Features

### Authentication & Account Lifecycle

* User registration and login
* Secure logout and token invalidation
* Access and refresh token flow
* Password reset via OTP
* Email verification via OTP

## Tech Stack

### Backend (Authify Core)

#### Core

* Java 25
* Spring Boot 3.5.7
* Spring MVC
* Spring Security
* Spring Data JPA
* Spring Validation
* Spring Mail
* Spring Actuator

#### API & Documentation

* Springdoc OpenAPI (Swagger UI)

#### Persistence

* PostgreSQL
* Hibernate (JPA)
* Flyway (Database migrations)

#### Security & Tokens

* JWT (jjwt)

#### Mapping & Utilities

* MapStruct
* Lombok

#### Build & Tooling

* Maven 3.9.9
* Docker
* Spring Boot DevTools

---

### Frontend (Authify UI)

* React 19 with TypeScript
* Vite (Development & build tool)
* React Router DOM (Routing)
* Axios (HTTP client)
* Ant Design + Ant Design Icons (UI components)
* Styled Components (Component-level styling)
* Yarn (Package manager)
* ESLint (Code quality)
