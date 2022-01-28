package com.example.training.redis.factory;

import org.springframework.http.HttpMethod;

public interface HttpMethodHandler {
    HttpMethod getType();

    boolean isCounterLower(int counter);
}
