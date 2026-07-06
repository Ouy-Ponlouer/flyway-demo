package com.example.flywaydemo.feature.user.service.impl;

import com.example.flywaydemo.dto.request.CreateUserRequest;
import com.example.flywaydemo.dto.request.UpdateUserRequest;
import com.example.flywaydemo.dto.response.PaginatedResponse;
import com.example.flywaydemo.dto.response.UserResponse;
import com.example.flywaydemo.feature.user.model.User;
import com.example.flywaydemo.common.exception.ResourceNotFoundException;
import com.example.flywaydemo.feature.user.repository.UserRepository;
import com.example.flywaydemo.feature.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PaginatedResponse<UserResponse> getAllUsersPaginated(int page, int size) {
        // Validate input
        if (page < 1) page = 1;
        if (size < 1) size = 10;
        if (size > 100) size = 100; // Max size limit

        int offset = (page - 1) * size;
        long totalElements = userRepository.countAll();
        List<UserResponse> content = userRepository.findAllWithPagination(offset, size).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        int totalPages = (int) Math.ceil((double) totalElements / size);
        boolean isLast = page >= totalPages;

        return PaginatedResponse.<UserResponse>builder()
                .content(content)
                .pageNumber(page)
                .pageSize(size)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .isLast(isLast)
                .build();
    }

    @Override
    public UserResponse getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    @Override
    @Transactional
    public UserResponse createUser(CreateUserRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .phone(request.getPhone())
                .build();
        userRepository.save(user);
        return mapToResponse(user);
    }

    @Override
    @Transactional
    public UserResponse updateUser(Long id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());

        userRepository.save(user);
        return mapToResponse(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        if (userRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public List<UserResponse> searchUsers(String username) {
        return userRepository.searchByUsername(username).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .phone(user.getPhone())
                .build();
    }
}
