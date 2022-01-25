package com.example.training.redis.dao;

import com.example.training.redis.model.UserCounter;

import java.util.Optional;

public interface UserCounterRepository {
    Optional<UserCounter> findByUsername(String http, String username);

    void save(String http, String username, UserCounter userCounter);
}
