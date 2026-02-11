# 📚 Library Book Management API

## Project Information
- **Port:** 8080
- **Base URL:** `http://localhost:8080`
- **Package Structure:** `auca.ac.rw.question1libraryapi`

---

## 📋 Endpoints

| Method | Endpoint | Description | Status Codes |
|--------|---------|-------------|--------------|
| GET | `/api/books` | Get all books | 200 OK |
| GET | `/api/books/{id}` | Get book by ID | 200 OK, 404 Not Found |
| GET | `/api/books/search?title={title}` | Search books by title | 200 OK |
| POST | `/api/books` | Add new book | 201 Created |
| DELETE | `/api/books/{id}` | Delete book | 204 No Content, 404 Not Found |

---

## 🎯 Sample Requests & Responses

### GET All Books
**Request:**
```http
GET http://localhost:8080/api/books