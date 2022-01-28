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
        return Optional.ofNullable(Optional.ofNullable(hashOperations.get(httpMethod, username))
                .orElseThrow(IllegalArgumentException::new));
    }

    @Override
    public void save(String httpMethod, String username, UserCounter userCounter) {
        hashOperations.put(httpMethod, username, userCounter);
    }
}
