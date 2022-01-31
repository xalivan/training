package com.example.training.redis.service;

import com.example.training.redis.model.UserCounter;
import org.springframework.http.HttpMethod;

import java.util.Optional;

public interface UserCounterService {
    void save(HttpMethod httpMethod, String username);

    void incrementAndUpdate(HttpMethod httpMethod, String username, UserCounter userCounter);

    Optional<UserCounter> findUserCounter(HttpMethod httpMethod, String userName);
}
