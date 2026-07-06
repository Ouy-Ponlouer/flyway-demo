package com.example.flywaydemo.feature.user.mapper;

import com.example.flywaydemo.feature.user.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {
    List<User> findAll();
    Optional<User> findById(@Param("id") Long id);
    void insert(User user);
    void update(User user);
    void deleteById(@Param("id") Long id);
    List<User> searchByUsername(@Param("username") String username);
    long countAll();
    List<User> findAllWithPagination(@Param("offset") int offset, @Param("limit") int limit);
}
