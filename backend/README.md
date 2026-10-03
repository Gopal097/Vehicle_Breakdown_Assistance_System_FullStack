# 🚗 Vehicle Breakdown Assistance System

A Spring Boot REST API for providing vehicle breakdown assistance. The system allows users to register, manage vehicles, 
request roadside assistance, and enables mechanics to manage and respond to breakdown requests securely using JWT Authentication.

---

## 📌 Features

- 👤 User Registration & Login
- 🔐 JWT Authentication & Authorization
- 🚗 Vehicle Management
- 🔧 Mechanic Management
- 🆘 Breakdown Request Management
- 🔍 Search API
- 📄 Pagination & Sorting
- ✅ Validation
- ⚠️ Global Exception Handling
- 📖 Swagger OpenAPI Documentation
- 🗄 PostgreSQL Database
- 📝 Logging using SLF4J

---

## 🛠 Tech Stack

- Java 17
- Spring Boot 3.5.x
- Spring Security
- Spring Data JPA
- PostgreSQL
- JWT (JSON Web Token)
- Maven
- Swagger (SpringDoc OpenAPI)

---

## 📂 Project Structure

```
src
 ├── config
 ├── controller
 ├── dto
 ├── entity
 ├── exception
 ├── repository
 ├── response
 ├── security
 ├── service
 ├── serviceImpl
 └── util
```

---

## 🔐 Authentication

Login Endpoint

```
POST /auth/login
```

After login, copy the generated JWT token and include it in every protected API request:

```
Authorization: Bearer YOUR_TOKEN
```

---

## 📌 API Endpoints

### User APIs

| Method | Endpoint |
|--------|----------|
| POST | /users |
| GET | /users |
| GET | /users/{id} |
| PUT | /users/{id} |
| DELETE | /users/{id} |

### Vehicle APIs

| Method | Endpoint |
|--------|----------|
| POST | /vehicles |
| GET | /vehicles |
| GET | /vehicles/{id} |
| PUT | /vehicles/{id} |
| DELETE | /vehicles/{id} |

### Mechanic APIs

| Method | Endpoint |
|--------|----------|
| POST | /mechanics |
| GET | /mechanics |
| GET | /mechanics/{id} |
| PUT | /mechanics/{id} |
| DELETE | /mechanics/{id} |

### Breakdown Request APIs

| Method | Endpoint |
|--------|----------|
| POST | /requests |
| GET | /requests |
| GET | /requests/{id} |
| PUT | /requests/{id} |
| DELETE | /requests/{id} |

---

## 🗄 Database Configuration

Database Name

```
vehicle_breakdown
```

Configure in `application.properties`

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/vehicle_breakdown
spring.datasource.username=postgres
spring.datasource.password=****
```

---

## ▶️ Run the Project

Clone the repository

```bash
git clone https://github.com/Gopal097/Vehicle_Breakdown_Assistance_System.git
```

Move into the project directory

```bash
cd Vehicle_Breakdown_Assistance_System
```

Run the application

```bash
mvn spring-boot:run
```

---

## 📖 Swagger UI

```
http://localhost:8080/swagger-ui/index.html
```

---

## 🚀 Future Enhancements

- Email Notifications
- Live Mechanic Tracking
- Google Maps Integration
- Payment Gateway
- Mobile Application

---

## 👨‍💻 Author

**Gopal Sahu**

GitHub: https://github.com/Gopal097
