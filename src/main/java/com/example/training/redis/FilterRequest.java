package com.example.training.redis;

import com.example.training.redis.factory.HttpMethodFactory;
import com.example.training.redis.model.UserCounter;
import com.example.training.redis.service.UserCounterService;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Optional;

@Slf4j
@EqualsAndHashCode(callSuper = true)
@Configuration
@AllArgsConstructor
public class FilterRequest extends OncePerRequestFilter {
    private final UserCounterService userCounterService;
    private final HttpMethodFactory httpMethodFactory;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        if (request.getMethod().equals(HttpMethod.GET.name()) || request.getMethod().equals(HttpMethod.PUT.name())) {
            String userNameFromContext = SecurityContextHolder.getContext().getAuthentication().getName();
            HttpMethod requestMethod = HttpMethod.valueOf(request.getMethod());
            Optional<UserCounter> userCounter = userCounterService.getUserCounter(requestMethod, userNameFromContext);
            if (userCounter.isEmpty()
                    || isTimeExpired(userCounter.get().getTimeExpired())
                    || httpMethodFactory.getHandler(requestMethod).isLimitReached(userCounter.get().getCounter())) {
                userCounterService.saveOrIncrement(requestMethod, userNameFromContext);
                filterChain.doFilter(request, response);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private boolean isTimeExpired(long userTime) {
        return userTime - ZonedDateTime.now(ZoneOffset.UTC).toInstant().toEpochMilli() < 0;
    }
}
