package com.example.training.redis.service;

import com.example.training.redis.model.UserCounter;
import org.springframework.http.HttpMethod;

import java.util.Optional;

public interface UserCounterService {
    void saveOrIncrement(HttpMethod httpMethod, String username);

    Optional<UserCounter> getUserCounter(HttpMethod httpMethod, String userName);

}
