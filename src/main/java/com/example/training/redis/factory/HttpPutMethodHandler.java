package com.example.training.redis.factory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;


@Component
public class HttpPutMethodHandler implements HttpMethodHandler {
    private final int limit;

    public HttpPutMethodHandler(@Value("${request.limit.put}") int limit) {
        this.limit = limit;
    }

    @Override
    public HttpMethod getType() {
        return HttpMethod.PUT;
    }

    @Override
    public boolean isCounterLower(int counter) {
        return limit > counter;
    }
}
