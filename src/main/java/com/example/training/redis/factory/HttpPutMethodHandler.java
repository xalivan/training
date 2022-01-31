package com.example.training.redis.factory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class HttpPutMethodHandler extends CounterLimit implements HttpMethodHandler {

    public HttpPutMethodHandler(@Value("${request.limit.put}") int limit) {
        super(limit);
    }

    @Override
    public HttpMethod getType() {
        return HttpMethod.PUT;
    }
}
