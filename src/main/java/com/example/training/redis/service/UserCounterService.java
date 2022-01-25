package com.example.training.redis.service;

import com.example.training.redis.model.UserCounter;

import java.util.Optional;

public interface UserCounterService {
    boolean saveOrIncrement(String httpMethod, String username);

    Optional<UserCounter> findUser(String httpMethod, String username);
}
