package com.example.flywaydemo.feature.user.repository;

import com.example.flywaydemo.feature.user.model.User;
import com.example.flywaydemo.feature.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository layer provides a clean abstraction over the MyBatis Mapper.
 * While Mappers can be used directly in Services, this layer is useful for:
 * 1. Adding complex business logic or data transformation before/after DB access.
 * 2. Combining multiple Mappers into a single logical data access unit.
 * 3. Providing a consistent interface that isn't tied directly to MyBatis.
 */
@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final UserMapper userMapper;

    public List<User> findAll() {
        return userMapper.findAll();
    }

    public Optional<User> findById(Long id) {
        return userMapper.findById(id);
    }

    public void save(User user) {
        if (user.getId() == null) {
            userMapper.insert(user);
        } else {
            userMapper.update(user);
        }
    }

    public void deleteById(Long id) {
        userMapper.deleteById(id);
    }

    public List<User> searchByUsername(String username) {
        return userMapper.searchByUsername(username);
    }

    public long countAll() {
        return userMapper.countAll();
    }

    public List<User> findAllWithPagination(int offset, int limit) {
        return userMapper.findAllWithPagination(offset, limit);
    }
}
