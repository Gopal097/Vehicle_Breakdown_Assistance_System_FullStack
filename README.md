# Vehicle Breakdown Assistance System (Full Stack)

React (Vite + Bootstrap) frontend + Spring Boot 3 / Spring Security / JWT / JPA backend + PostgreSQL.

    Vehicle_Breakdown_Assistance_System/
      backend/    Spring Boot REST API (port 8080)
      frontend/   React app (port 5173)

## 1. Prerequisites
JDK 17+ (21 works), PostgreSQL, Node.js 18+.

## 2. Database
Create an empty database once (tables are created automatically on first start):

    CREATE DATABASE vehicle_breakdown;

`backend/src/main/resources/application.properties` reads `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`,
`JWT_SECRET`, `ADMIN_EMAIL`, `ADMIN_PASSWORD` from environment variables and falls back to the defaults in the file.
If your PostgreSQL password differs, set `DB_PASSWORD` (or edit that one line).

## 3. Run
Backend (new terminal):

    cd backend
    ./mvnw spring-boot:run          # Windows: mvnw.cmd spring-boot:run

Frontend (second terminal):

    cd frontend
    npm install
    npm run dev                     # open http://localhost:5173

Swagger UI: http://localhost:8080/swagger-ui.html (log in via `POST /auth/login`, copy the token, press **Authorize**).

## 4. Demo accounts (created automatically on first start)
| Role | Email | Password |
|---|---|---|
| Admin | admin@vehiclebreakdown.com | Admin@123 |
| Mechanic | ravi@vehiclebreakdown.com (also suresh@, imran@, anitha@vehiclebreakdown.com) | Mechanic@123 |
| User | register your own on the Register page | - |

## 5. 8-minute demo script
1. **Register** a new user, then **log in** (JWT issued; role decides which screens you get).
2. **My Vehicles**: add a car. **Get Help**: raise a request (Flat tyre, location).
3. **Dashboard**: the request shows as *Pending*.
4. Log out, log in as **admin**: **Requests** tab, assign *Ravi Kumar* (status becomes *Assigned*).
5. Log in as **ravi@vehiclebreakdown.com**: **My Jobs**, press *Start job*, then *Mark resolved*.
6. Log in as the user again: the status shows *Resolved* and the mechanic's name and phone.
7. Show Swagger: one API, then its Controller -> Service -> Repository -> table row in PostgreSQL.

## 6. Security design (talking points)
- Passwords stored with BCrypt; the password is write-only in JSON (never returned by any API).
- JWT (HS256, 24 h). The secret comes from configuration.
- Three roles enforced on the server in `SecurityConfig`: USER, MECHANIC, ADMIN.
  Public registration always creates a USER; admin and mechanic accounts are created by the system/admin.
- Ownership checks: a user can only create requests for and delete their *own* vehicles; a mechanic can only update jobs assigned to them.
- 401 = not logged in / bad token, 403 = wrong role, 400 = validation/business rule, 404 = not found.

## 7. Likely interview questions
- *How does React talk to Spring Boot?* Axios sends REST/JSON calls; an interceptor adds the `Authorization: Bearer <token>` header.
- *How is role-based access done?* Role is loaded into Spring Security authorities (`ROLE_ADMIN` ...) and matched with `hasRole` rules.
- *Why DTOs / global exception handler?* Decouple API from entities; one place that maps errors to proper HTTP codes.
- *Request lifecycle?* Pending -> Assigned (admin) -> In Progress -> Resolved (mechanic).
