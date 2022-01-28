package com.example.training.redis.factory;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Component
public class HttpMethodFactoryImpl implements HttpMethodFactory {
    private final Map<HttpMethod, HttpMethodHandler> httpMethodHandlerMapping;

    public HttpMethodFactoryImpl(List<HttpMethodHandler> services) {
        this.httpMethodHandlerMapping = putHttpMethodHandlerMapping(services);
    }

    @Override
    public HttpMethodHandler getHandler(HttpMethod httpMethod) {
        return httpMethodHandlerMapping.get(httpMethod);
    }

    private Map<HttpMethod, HttpMethodHandler> putHttpMethodHandlerMapping(List<HttpMethodHandler> services) {
        return services.stream()
                .collect(Collectors.toUnmodifiableMap(HttpMethodHandler::getType, Function.identity()));
    }
}
