package com.example.flywaydemo.feature.user.service;

import com.example.flywaydemo.dto.request.CreateUserRequest;
import com.example.flywaydemo.dto.request.UpdateUserRequest;
import com.example.flywaydemo.dto.response.PaginatedResponse;
import com.example.flywaydemo.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> getAllUsers();
    PaginatedResponse<UserResponse> getAllUsersPaginated(int page, int size);
    UserResponse getUserById(Long id);
    UserResponse createUser(CreateUserRequest request);
    UserResponse updateUser(Long id, UpdateUserRequest request);
    void deleteUser(Long id);
    List<UserResponse> searchUsers(String username);
}
