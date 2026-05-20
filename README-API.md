# User Management API Examples

## Base URL
`http://localhost:8080/api/users`

## 1. Get All Users
**Request:**
```bash
curl -X GET http://localhost:8080/api/users
```

**Response:**
```json
[
  {
    "id": 1,
    "username": "johndoe",
    "email": "john@example.com",
    "phone": "1234567890"
  }
]
```

## 2. Search Users by Username
**Request:**
```bash
curl -X GET "http://localhost:8080/api/users?username=john"
```

## 3. Get User by ID
**Request:**
```bash
curl -X GET http://localhost:8080/api/users/1
```

**Response:**
```json
{
  "id": 1,
  "username": "johndoe",
  "email": "john@example.com",
  "phone": "1234567890"
}
```

## 4. Create User
**Request:**
```bash
curl -X POST http://localhost:8080/api/users \
     -H "Content-Type: application/json" \
     -d '{
       "username": "johndoe",
       "email": "john@example.com",
       "phone": "1234567890"
     }'
```

**Response:**
```json
{
  "id": 1,
  "username": "johndoe",
  "email": "john@example.com",
  "phone": "1234567890"
}
```

## 5. Update User
**Request:**
```bash
curl -X PUT http://localhost:8080/api/users/1 \
     -H "Content-Type: application/json" \
     -d '{
       "username": "john_updated",
       "email": "john_updated@example.com",
       "phone": "0987654321"
     }'
```

## 6. Delete User
**Request:**
```bash
curl -X DELETE http://localhost:8080/api/users/1
```

## Validation Error Example
**Request:**
```bash
curl -X POST http://localhost:8080/api/users \
     -H "Content-Type: application/json" \
     -d '{
       "username": "",
       "email": "invalid-email"
     }'
```

**Response:**
```json
{
  "timestamp": "2026-05-20T10:10:00",
  "errors": {
    "username": "Username is required",
    "email": "Invalid email format"
  }
}
```
