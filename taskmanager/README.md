# Task Management System
### Author: Talgatbek Sharafatdin

## Stack
- Spring Boot 3.2, Spring Security + JWT
- PostgreSQL, Spring Data JPA, MapStruct
- Swagger UI, Lombok, Docker

## Run with Docker
```bash
docker-compose up --build
```

## API docs
http://localhost:8080/swagger-ui.html

## Auth endpoints
- POST /api/auth/register
- POST /api/auth/login

## Main endpoints (require Bearer token)
- GET/POST /api/tasks  (pagination, sorting, search, filter)
- GET/POST/PUT/DELETE /api/projects
- GET/POST/PUT/DELETE /api/categories
- POST /api/tasks/{id}/comments
- POST /api/tasks/{id}/attachments (file upload)
- GET  /api/attachments/{id}/download (file download)
