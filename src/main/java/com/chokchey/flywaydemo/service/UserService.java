package com.chokchey.flywaydemo.service;

import com.chokchey.flywaydemo.dto.request.CreateUserRequest;
import com.chokchey.flywaydemo.dto.request.UpdateUserRequest;
import com.chokchey.flywaydemo.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> getAllUsers();
    UserResponse getUserById(Long id);
    UserResponse createUser(CreateUserRequest request);
    UserResponse updateUser(Long id, UpdateUserRequest request);
    void deleteUser(Long id);
    List<UserResponse> searchUsers(String username);
}
