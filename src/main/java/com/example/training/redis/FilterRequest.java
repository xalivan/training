package com.example.training.redis;

import com.example.training.security.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static com.example.training.redis.UserCounterService.LIMIT_GET;
import static com.example.training.redis.UserCounterService.LIMIT_PUT;

@Configuration
@AllArgsConstructor
public class FilterRequest extends OncePerRequestFilter {
    private final UserDetailsServiceImpl userDetailsService;
    private final UserCounterService service;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<UserDetails> userDetails = userDetails(userName);

        if (userDetails.isPresent()) {
            String method = request.getMethod();
            Optional<UserCounter> userCounter = service.findUser(method, userName);
            String username = userDetails.get().getUsername();
            if (userCounter.isEmpty()) {
                service.save(method, username);
            } else {
                int counter = userCounter.get().getCounter();
                if (isGet(method, counter)) {
                    service.update(method, username);
                } else if (isPut(method, counter)) {
                    service.update(method, username);
                } else {
                    setUnauthorized(response);
                }
            }
        }
    }

    private boolean isGet(String httpMethod, int counter) {
        return httpMethod.equals("GET") && counter <= LIMIT_GET;
    }

    private boolean isPut(String httpMethod, int counter) {
        return httpMethod.equals("PUT") && counter <= LIMIT_PUT;
    }

    private Optional<UserDetails> userDetails(String userName) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        return Optional.ofNullable(userDetails);
    }

    private void setUnauthorized(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
