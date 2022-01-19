package com.example.training.redis;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserLimitRepository {
    private final HashOperations<String, String, UserLimit> hashOperations;
    private final static String USER_LIMIT = "user";

    public UserLimitRepository(RedisTemplate<String, UserLimit> redisTemplate) {
        hashOperations = redisTemplate.opsForHash();
    }

    public void save(String username, UserLimit userCounter) {
        hashOperations.put(USER_LIMIT, username, userCounter);
    }

    public Optional<UserLimit> findByUsername(String username) {
        return Optional.ofNullable(hashOperations.get(USER_LIMIT, username));
    }
}
