package com.example.training.redis.factory;

import org.springframework.http.HttpMethod;

public interface HttpMethodFactory {
    HttpMethodHandler getHandler(HttpMethod httpMethod);
}
