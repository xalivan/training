package com.example.training.redis.factory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class GetMethod implements HttpMethodHandler {
    private final int limit;

    public GetMethod(@Value("${request.limit.get}") int limit) {
        this.limit = limit;
    }

    @Override
    public HttpMethod getType() {
        return HttpMethod.GET;
    }

    @Override
    public boolean isLimitReached(int counter) {
        return limit > counter;
    }
}
