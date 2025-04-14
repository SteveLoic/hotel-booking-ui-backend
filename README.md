# 🌐 Fullstack Anwendung – Angular + Spring Boot + MySQL + Docker

# Beschreibung:

Hotel Buchung ist eine webbasierte Anwendung, die Nutzern ermöglicht, Hotels und Unterkünfte zu suchen, zu vergleichen und zu buchen

Eine moderne Fullstack-Webanwendung mit Angular (inkl. Signals und NgRx Signal Store) im Frontend und einem Spring Boot Backend mit JWT-Authentifizierung,
rollenbasierter Autorisierung sowie integrierter OpenAPI-Dokumentation.
Die Applikation verwendet MySQL zur Datenspeicherung und ist vollständig dockerisiert für einfache Entwicklung und Deployment.

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

## Projektstruktur

├── backend/ │ ├── Dockerfile │ ├── docker-compose.yml │ ├── src/ │ ├── pom.xml │ ├── target/ │ └── application.properties ├── frontend/ │ ├── Dockerfile │ ├── src/ │ ├── package.json │ ├── angular.json │ └── ngrx-signalstore-config.ts └── docker-compose.yml

## 🛠️ Setup & Start

## 1. 🧰 Voraussetzungen

Bitte stelle sicher, dass folgende Tools installiert sind:

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)
- (Optional) [Node.js & Angular CLI](https://angular.io/cli) – für lokale Frontend-Entwicklung
- (Optional) [Java 17+] – für lokale Backend-Entwicklung

---

### 2. **Frontend starten:**

Um das **Frontend** lokal zu starten, kannst du den **Angular Development Server** verwenden.

```bash
cd hotel-booking-ui
npm install
ng serve

Zugänglich unter: http://localhost:4200
```

### 3. **Backend starten:**

Um das backend lokal starten.

```bash
cd hotel_booking_app
./mvnw spring-boot:run

Zugänglich unter: http://localhost:5051
Swagger-UI: http://localhost:5051/api/v1/swagger-ui/index.html
```

### 4. **Die Gesamte Anwendung mit Docker Compose starten**

```bash
docker-compose up --build

Dadurch werden:

(hotel-booking-ui) Das Angular Frontend  auf : http://localhost:8080 bereitgestellt

(hotel-booking-api) Das Spring Boot Backend auf : http://localhost:5051 bereitgestellt

Swagger UI: http://localhost:5051/api/v1/swagger-ui/index.html  bereitgestellt
```

## Standardbenutzer

Für den schnellen Einstieg in die Anwendung bieten wir drei Standardbenutzer an: **Admin** und **Customer**. Diese Benutzer können direkt verwendet werden, um die Funktionalitäten der Anwendung zu testen.

### Standardbenutzer:

#### 1. Admin-Benutzer:

- **email:** `john.smith@test.com`
- **Password:** `password1234`
- **Rolle:** `ADMIN`

#### 2. Customer-Benutzer:

- **email:** `heidi.horton@test.com`
- **Passwort:** `password1234`
- **Rolle:** `CUSTOMER`

#### 3. Weitere Customer-Benutzer:

- **Benutzername:** `john.doe@test.com`
- **Passwort:** `password1234`
- **Rolle:** `CUSTOMER`
