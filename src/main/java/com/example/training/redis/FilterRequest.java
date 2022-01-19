package com.example.training.redis;

import com.example.training.security.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

@Component
@Slf4j
@AllArgsConstructor
public class FilterRequest extends OncePerRequestFilter {
    private final UserLimitService userLimitService;
    public static final int LIMIT_GET = 50;
    public static final int LIMIT_PUT = 5;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String method = request.getMethod();
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDetails userDetails = userDetailsService.loadUserByUsername(login);
        LocalDate countExpiredDay = getCountExpiredDay(login);
        if (userDetails != null) {
            Optional<UserLimit> item = userLimitService.find(userDetails.getUsername());
            if (item.isEmpty()) {
                userLimitService.save(userDetails.getUsername(), 1, 1);
            } else {
                updateCount(login, method, request, response, filterChain);
                assert countExpiredDay != null;
                if (countExpiredDay.equals(LocalDate.now())) {
                    setUnauthorized(response);
                }
                setUnauthorized(response);
            }
        }
    }

    private void updateCount(String login, String method, HttpServletRequest request,
                             @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        int countMethodGet = getCountMethodGet(login);
        int countMethodPut = getCountMethodPut(login);
        if (method.equals("GET") && countMethodGet < LIMIT_GET) {
            userLimitService.set(login, countMethodGet + 1, countMethodPut, LocalDate.now());
            filterChain.doFilter(request, response);
        }
        if (method.equals("PUT") && countMethodPut < LIMIT_PUT) {
            userLimitService.set(login, countMethodGet, countMethodPut + 1, LocalDate.now());
            filterChain.doFilter(request, response);
        }
    }

    private int getCountMethodGet(String login) {
        if (userLimitService.find(login).isPresent()) {
            return userLimitService.find(login).get().getCountMethodGet();
        }
        return 0;
    }

    private int getCountMethodPut(String login) {
        if (userLimitService.find(login).isPresent()) {
            return userLimitService.find(login).get().getCountMethodPut();
        }
        return 0;
    }

    private LocalDate getCountExpiredDay(String login) {
        if (userLimitService.find(login).isPresent()) {
            return userLimitService.find(login).get().getExpired();
        }
        return null;
    }

    private void setUnauthorized(HttpServletResponse response) {
        SecurityContextHolder.clearContext();
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

}
