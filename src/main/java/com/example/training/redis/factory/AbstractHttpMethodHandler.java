package com.example.training.redis.factory;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;

@AllArgsConstructor
public abstract class AbstractHttpMethodHandler implements HttpMethodHandler {
    private final int limit;

    @Override
    public abstract HttpMethod getType();

    @Override
    public boolean isCounterLowerThanAllowedLimit(int counter) {
        return limit > counter;
    }
}
