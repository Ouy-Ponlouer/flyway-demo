# Pagination Response Implementation

## Overview
Successfully implemented pagination support with standardized API response format for the User Management API.

## Changes Made

### 1. **New Response Wrapper Classes**

#### ApiResponse.java
- Generic wrapper for all API responses
- Contains fields: `success`, `status`, `message`, `data`
- Utility methods: `ok()`, `created()`, `error()`

#### PaginatedResponse.java
- Generic pagination wrapper containing:
  - `content`: List of items
  - `pageNumber`: Current page (1-indexed)
  - `pageSize`: Items per page
  - `totalElements`: Total count of all items
  - `totalPages`: Total number of pages
  - `isLast`: Boolean indicating if this is the last page

### 2. **Database Layer Updates**

#### UserMapper.xml - Added
- `countAll()`: Returns total count of users
- `findAllWithPagination()`: Returns paginated results with LIMIT and OFFSET

#### UserMapper.java - Added
- `long countAll()`
- `List<User> findAllWithPagination(@Param("offset") int offset, @Param("limit") int limit)`

#### UserRepository.java - Added
- `long countAll()`
- `List<User> findAllWithPagination(int offset, int limit)`

### 3. **Service Layer**

#### UserService.java - Added
- `PaginatedResponse<UserResponse> getAllUsersPaginated(int page, int size)`

#### UserServiceImpl.java - Implemented
- Pagination logic with validation:
  - Min page: 1
  - Default size: 10
  - Max size: 100
- Calculates total pages and isLast flag
- Streams results to DTOs

### 4. **Controller Layer Updates**

#### UserController.java - Updated
All endpoints now return standardized `ApiResponse` format:

| Endpoint | Method | Response |
|----------|--------|----------|
| `/api/users` | GET | `ApiResponse<PaginatedResponse<UserResponse>>` |
| `/api/users/{id}` | GET | `ApiResponse<UserResponse>` |
| `/api/users` | POST | `ApiResponse<UserResponse>` (Status 201) |
| `/api/users/{id}` | PUT | `ApiResponse<UserResponse>` |
| `/api/users/{id}` | DELETE | `ApiResponse<Void>` |

## API Response Examples

### Get All Users (Paginated)
**Request:**
```
GET /api/users?page=1&size=10
```

**Response:**
```json
{
  "success": true,
  "status": 200,
  "message": "Get User successfully",
  "data": {
    "content": [
      {
        "id": 1,
        "username": "Charlie",
        "email": "charlie@example.com",
        "phone": "09868665"
      },
      {
        "id": 2,
        "username": "John",
        "email": "john@example.com",
        "phone": "0987654321"
      }
    ],
    "page_number": 1,
    "page_size": 10,
    "total_elements": 25,
    "total_pages": 3,
    "is_last": false
  }
}
```

### Get User by ID
**Request:**
```
GET /api/users/1
```

**Response:**
```json
{
  "success": true,
  "status": 200,
  "message": "Get User successfully",
  "data": {
    "id": 1,
    "username": "Charlie",
    "email": "charlie@example.com",
    "phone": "09868665"
  }
}
```

### Create User
**Request:**
```
POST /api/users
Content-Type: application/json

{
  "username": "Alice",
  "email": "alice@example.com",
  "phone": "0912345678"
}
```

**Response (Status 201):**
```json
{
  "success": true,
  "status": 201,
  "message": "User created successfully",
  "data": {
    "id": 3,
    "username": "Alice",
    "email": "alice@example.com",
    "phone": "0912345678"
  }
}
```

### Update User
**Request:**
```
PUT /api/users/1
Content-Type: application/json

{
  "username": "Charlie Updated",
  "email": "charlie.updated@example.com",
  "phone": "09999999"
}
```

**Response:**
```json
{
  "success": true,
  "status": 200,
  "message": "User updated successfully",
  "data": {
    "id": 1,
    "username": "Charlie Updated",
    "email": "charlie.updated@example.com",
    "phone": "09999999"
  }
}
```

### Delete User
**Request:**
```
DELETE /api/users/1
```

**Response:**
```json
{
  "success": true,
  "status": 200,
  "message": "User deleted successfully",
  "data": null
}
```

### Search Users
**Request:**
```
GET /api/users?username=charlie
```

**Response:**
```json
{
  "success": true,
  "status": 200,
  "message": "Get User successfully",
  "data": {
    "content": [
      {
        "id": 1,
        "username": "Charlie",
        "email": "charlie@example.com",
        "phone": "09868665"
      }
    ],
    "page_number": 1,
    "page_size": 1,
    "total_elements": 1,
    "total_pages": 1,
    "is_last": true
  }
}
```

## Features

✅ **Standardized Response Format**: All endpoints return consistent JSON structure
✅ **Pagination Support**: Page and size parameters with validation
✅ **HTTP Status Codes**: Proper status codes (200, 201, etc.)
✅ **Metadata**: Total elements, total pages, and isLast flag
✅ **Search Support**: Search users by username with pagination
✅ **Input Validation**: Page/size validation with defaults and limits
✅ **Backward Compatibility**: Old `getAllUsers()` method still available

## Query Parameters

### Pagination Parameters
- `page`: Page number (default: 1, min: 1)
- `size`: Items per page (default: 10, max: 100)
- `username`: Optional search filter (ignores pagination if provided with complete result)

## Status Codes
- `200`: Successful GET, PUT, DELETE operations
- `201`: Successful POST (Create)
- `404`: Not Found (when resource doesn't exist)
- `400`: Bad Request (validation errors)
- `500`: Internal Server Error

## Build Status
✅ Project builds successfully with no compilation errors

