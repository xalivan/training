package com.example.training.redis;

import com.example.training.redis.factory.HttpMethodFactory;
import com.example.training.redis.model.UserCounter;
import com.example.training.redis.service.UserCounterService;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Optional;

@EqualsAndHashCode(callSuper = true)
@Component
@AllArgsConstructor
public class FilterRequest extends OncePerRequestFilter {
    private final UserCounterService userCounterService;
    private final HttpMethodFactory httpMethodFactory;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        HttpMethod requestMethod = HttpMethod.valueOf(request.getMethod());
        if (HttpMethod.GET.equals(requestMethod) || HttpMethod.PUT.equals(requestMethod)) {
            String userNameFromContext = SecurityContextHolder.getContext().getAuthentication().getName();
            Optional<UserCounter> userCounter = userCounterService.findUserCounter(requestMethod, userNameFromContext);
            if (userCounter.isEmpty() || isTimeExpired(userCounter.get().getTimeExpired())) {
                userCounterService.save(requestMethod, userNameFromContext);
            } else if (httpMethodFactory.getHandler(requestMethod).isCounterLowerThanAllowedLimit(userCounter.get().getCounter())) {
                userCounterService.incrementAndUpdate(requestMethod, userNameFromContext, userCounter.get());
            } else {
                SecurityContextHolder.clearContext();
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean isTimeExpired(long userTime) {
        return userTime - ZonedDateTime.now(ZoneOffset.UTC).toInstant().toEpochMilli() < 0;
    }
}
