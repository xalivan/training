package com.example.training.redis.dao;

import com.example.training.redis.model.UserCounter;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserCounterRepositoryImpl implements UserCounterRepository {
    private final HashOperations<String, String, UserCounter> hashOperations;

    public UserCounterRepositoryImpl(RedisTemplate<String, UserCounter> redisTemplate) {
        hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public Optional<UserCounter> findByUsername(String httpMethod, String username) {
        return Optional.ofNullable(hashOperations.get(httpMethod, username));
    }

    @Override
    public void save(String http, String username, UserCounter userCounter) {
        hashOperations.put(http, username, userCounter);
    }
}
