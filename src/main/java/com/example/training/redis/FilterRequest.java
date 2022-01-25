package com.example.training.redis;

import com.example.training.redis.service.UserCounterServiceImpl;
import com.example.training.security.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@EqualsAndHashCode(callSuper = true)
@Configuration
@AllArgsConstructor
public class FilterRequest extends OncePerRequestFilter {
    private final UserDetailsServiceImpl userDetailsService;
    private final UserCounterServiceImpl service;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        if (request.getMethod().equals(HttpMethod.GET.name()) || request.getMethod().equals(HttpMethod.PUT.name())) {
            String userNameFromContext = SecurityContextHolder.getContext().getAuthentication().getName();
            String userNameFromDetails = getUserDetails(userNameFromContext).getUsername();
            String requestMethod = request.getMethod();
            if (service.saveOrIncrement(requestMethod, userNameFromDetails)) {
                filterChain.doFilter(request, response);
            } else {
                setUnauthorized(response);
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private UserDetails getUserDetails(String userName) {
        return userDetailsService.loadUserByUsername(userName);
    }

    private void setUnauthorized(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
