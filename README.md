# Project Tracker Backend (Spring Boot)

This repository contains the Spring Boot REST API for the mini project management system. Students can record software projects, update their details and status, and delete them. Data is stored in MySQL (local or Aiven), so it remains available after the application restarts.

All commands below run from the root of this repository.

## Prerequisites

- JDK 21
- Maven (this project does not include a Maven wrapper, so `mvn` must be on your `PATH`)
- A running MySQL server (local or Aiven)

## MySQL setup

Create the database once. Hibernate creates the `projects` table on startup.

```sql
CREATE DATABASE ttproject;
```

## Environment variables

Create a local `.env` file from the example and fill in your values. It is gitignored, so it will not be committed.

```bash
cp .env.example .env
```

| Variable       | Local MySQL             | Aiven                           |
|----------------|-------------------------|---------------------------------|
| `DB_HOST`      | `localhost`             | Host from the Aiven console     |
| `DB_PORT`      | `3306`                  | Port from the Aiven console     |
| `DB_NAME`      | `ttproject`             | Your database name              |
| `DB_SSL_MODE`  | `DISABLED`              | `REQUIRED`                      |
| `DB_USERNAME`  | Your MySQL username     | Username from the Aiven console |
| `DB_PASSWORD`  | Your MySQL password     | Password from the Aiven console |
| `FRONTEND_URL` | `http://localhost:5173` | Your deployed frontend URL      |

`FRONTEND_URL` is the only origin allowed by CORS.

## Running

The `.env` file is loaded from the working directory, so start the backend from the repository root:

```bash
mvn spring-boot:run   # start the backend (default port 8080)
```

To build the application without running it:

```bash
mvn clean package
```

## REST API

| Method | Path                 | Purpose                                    | Success |
|--------|----------------------|--------------------------------------------|---------|
| GET    | `/api/projects`      | List all projects, earliest deadline first | 200     |
| GET    | `/api/projects/{id}` | Get one project                            | 200     |
| POST   | `/api/projects`      | Create a project                           | 201     |
| PUT    | `/api/projects/{id}` | Update a project, including its status     | 200     |
| DELETE | `/api/projects/{id}` | Delete a project                           | 204     |

Request body for `POST` and `PUT`:

```json
{
  "name": "Portfolio site",
  "description": "Personal portfolio built with React",
  "startDate": "2026-11-01",
  "deadline": "2026-12-01",
  "status": "NOT_STARTED"
}
```

All five fields are required. `status` must be `NOT_STARTED`, `IN_PROGRESS`, or `COMPLETED`.

Errors return `status` and `message`. Validation failures also return `fieldErrors`:

```json
{
  "status": 400,
  "message": "Please correct the highlighted fields",
  "fieldErrors": {
    "deadline": "Deadline cannot be earlier than the start date"
  }
}
```

## Source layout

Configuration is in `src/main/resources/application.yml`. The code is under `src/main/java/com/ttproject/`:

- `controller/` – REST endpoints
- `service/` – business logic and date validation
- `repository/` – Spring Data JPA access
- `entity/` – the `Project` entity and `ProjectStatus` enum
- `dto/` – request, response, and error bodies
- `exception/` – custom exceptions and the global error handler
- `config/` – CORS configuration
