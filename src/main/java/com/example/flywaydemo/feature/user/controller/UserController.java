package com.example.flywaydemo.feature.user.controller;

import com.example.flywaydemo.dto.request.CreateUserRequest;
import com.example.flywaydemo.dto.request.UpdateUserRequest;
import com.example.flywaydemo.dto.response.ApiResponse;
import com.example.flywaydemo.dto.response.PaginatedResponse;
import com.example.flywaydemo.dto.response.UserResponse;
import com.example.flywaydemo.feature.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "Endpoints for managing users")
public class UserController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "Get all users", description = "Returns a paginated list of all users with pagination support")
    public ResponseEntity<ApiResponse<PaginatedResponse<UserResponse>>> getAllUsers(
            @RequestParam(required = false) String username,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        if (username != null && !username.isEmpty()) {
            List<UserResponse> results = userService.searchUsers(username);
            PaginatedResponse<UserResponse> response = PaginatedResponse.<UserResponse>builder()
                    .content(results)
                    .pageNumber(1)
                    .pageSize(results.size())
                    .totalElements(results.size())
                    .totalPages(1)
                    .isLast(true)
                    .build();
            return ResponseEntity.ok(ApiResponse.ok(response, "Get User successfully"));
        }
        PaginatedResponse<UserResponse> response = userService.getAllUsersPaginated(page, size);
        return ResponseEntity.ok(ApiResponse.ok(response, "Get User successfully"));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Returns a single user based on their unique ID")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long id) {
        UserResponse user = userService.getUserById(id);
        return ResponseEntity.ok(ApiResponse.ok(user, "Get User successfully"));
    }

    @PostMapping
    @Operation(summary = "Create new user", description = "Creates a new user record in the database")
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@Valid @RequestBody CreateUserRequest request) {
        UserResponse user = userService.createUser(request);
        return new ResponseEntity<>(ApiResponse.created(user, "User created successfully"), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update user", description = "Updates an existing user's information")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(@PathVariable Long id, @Valid @RequestBody UpdateUserRequest request) {
        UserResponse user = userService.updateUser(id, request);
        return ResponseEntity.ok(ApiResponse.ok(user, "User updated successfully"));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user", description = "Permanently removes a user from the system")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(ApiResponse.ok(null, "User deleted successfully"));
    }
}
