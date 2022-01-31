package com.example.training.security;

import com.example.training.model.Role;
import com.example.training.redis.FilterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final FilterRequest filterRequest;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.PUT, "/users", "/line").hasAuthority(Role.ADMIN.name())
                .antMatchers(HttpMethod.DELETE, "/users", "/line").hasAuthority(Role.ADMIN.name())
                .anyRequest().authenticated()
                .and()
                .addFilterAfter(filterRequest, BasicAuthenticationFilter.class)
                .formLogin()
                .permitAll()
                .and()
                .httpBasic()
                .and()
                .logout();
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
