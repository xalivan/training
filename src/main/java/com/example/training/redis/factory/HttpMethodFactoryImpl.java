package com.example.training.redis.factory;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
public class HttpMethodFactoryImpl implements HttpMethodFactory {
    private final Map<HttpMethod, HttpMethodHandler> httpMethodHandlerMapping;

    public HttpMethodFactoryImpl(List<HttpMethodHandler> services) {
        this.httpMethodHandlerMapping = services.stream().collect(Collectors.toMap(HttpMethodHandler::getType, service -> service));
    }

    @Override
    public HttpMethodHandler getHandler(HttpMethod httpMethod) {
        return httpMethodHandlerMapping.get(httpMethod);
    }
}
