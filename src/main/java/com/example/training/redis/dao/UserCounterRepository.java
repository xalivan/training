package com.example.training.redis.dao;

import com.example.training.redis.model.UserCounter;

import java.util.Optional;

public interface UserCounterRepository {
    Optional<UserCounter> findByUsername(String httpMethod, String username);

    void save(String httpMethod, String username, UserCounter userCounter);
}
