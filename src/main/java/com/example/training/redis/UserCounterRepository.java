package com.example.training.redis;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserCounterRepository {
    private final HashOperations<String, String, UserCounter> hashOperations;

    public UserCounterRepository(RedisTemplate<String, UserCounter> redisTemplate) {
        hashOperations = redisTemplate.opsForHash();
    }

    public Optional<UserCounter> findByUsername(String http, String username) {
        return Optional.ofNullable(hashOperations.get(http, username));
    }

    public void save(String http, String username, UserCounter userCounter) {
        hashOperations.put(http, username, userCounter);
    }
}
