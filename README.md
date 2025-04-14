# 🌐 Fullstack Anwendung – Angular + Spring Boot + MySQL + Docker

Beschreibung:
Hotel Buchung ist eine webbasierte Anwendung, die Nutzern ermöglicht, Hotels und Unterkünfte zu suchen, zu vergleichen und zu buchen

## Eine moderne Fullstack-Webanwendung mit Angular (inkl. Signals und NgRx Signal Store) im Frontend und einem Spring Boot Backend mit JWT-Authentifizierung, rollenbasierter Autorisierung sowie integrierter OpenAPI-Dokumentation. Die Applikation verwendet MySQL zur Datenspeicherung und ist vollständig dockerisiert für einfache Entwicklung und Deployment.

## 🚀 Technologien

### 🔧 Frontend

- **Angular 17+**
- **Angular Signals**
- **NgRx Signal Store** (für das Zustandsmanagement)

### 🖥 Backend

- **Spring Boot 3+**
- **Spring Security** (JWT-Authentifizierung)
- **Spring Data JPA**
- **MySQL**

### 🐳 DevOps

- **Docker**
- **Docker Compose**

---

## 📊 Übersicht der Technologien

| **Komponente** | **Technologie**                         |
| -------------- | --------------------------------------- |
| **Frontend**   | Angular 17+, NgRx Signal Store, Signals |
| **Backend**    | Spring Boot, JWT, MySQL                 |
| **DevOps**     | Docker, Docker Compose                  |

---

## 🛠️ Projekstruktur

├── backend/
│ ├── Dockerfile
│ ├── docker-compose.yml
│ ├── src/
│ ├── pom.xml
│ ├── target/
│ └── application.properties
├── frontend/
│ ├── Dockerfile
│ ├── src/
│ ├── package.json
│ ├── angular.json
│ └── ngrx-signalstore-config.ts
└── docker-compose.yml

## 🛠️ Setup & Start

### 1. **Frontend starten:**

Um das **Frontend** lokal ohne Docker zu starten, kannst du den **Angular Development Server** verwenden.

```bash
cd hotel-booking-ui
npm install
ng serve

Zugänglich unter: http://localhost:4200
```

Um das **Frontend** mit Docker und Nginx starten.

```bash

cd hotel-booking-ui
docker build -t hotel-booking-ui:latest .
docker run -p 8080:8080 hotel-booking-ui:latest

Zugänglich unter:  http://localhost:8080
```

### 2. **Backend starten:**

```bash
cd hotel_booking_app
./mvnw spring-boot:run

Zugänglich unter: http://localhost:5051
Swagger-UI: http://localhost:5051/api/v1/swagger-ui/index.html
```

### 2. **Mit Docker starten:**

```bash
docker-compose up --build

Zugänglich unter:

Frontend: http://localhost:8080

Backend API: http://localhost:5051

Swagger UI: http://localhost:5051/api/v1/swagger-ui/index.html
```
