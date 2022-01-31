package com.example.training.redis.factory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class HttpGetMethodHandler extends AbstractHttpMethodHandler {

    public HttpGetMethodHandler(@Value("${request.limit.get}") int limit) {
        super(limit);
    }

    @Override
    public HttpMethod getType() {
        return HttpMethod.GET;
    }
}
