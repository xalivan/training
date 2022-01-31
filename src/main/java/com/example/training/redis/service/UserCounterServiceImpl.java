package com.example.training.redis.service;

import com.example.training.redis.dao.UserCounterRepository;
import com.example.training.redis.dao.UserCounterRepositoryImpl;
import com.example.training.redis.model.UserCounter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Optional;

@Slf4j
@Service
public class UserCounterServiceImpl implements UserCounterService {
    private final long timeExpiredInMinutes;
    private final UserCounterRepository userCounterRepository;

    public UserCounterServiceImpl(@Value("${request.counter.expiration-time}") long timeExpiredInMinutes,
                                  UserCounterRepositoryImpl userCounterRepository) {
        this.timeExpiredInMinutes = timeExpiredInMinutes;
        this.userCounterRepository = userCounterRepository;
    }

    @Override
    public void save(HttpMethod httpMethod, String username) {
        long expirationTime = ZonedDateTime.now(ZoneOffset.UTC).toInstant().toEpochMilli() + timeExpiredInMinutes * 60 * 1000; // minute convert to milliseconds
        userCounterRepository.save(httpMethod.name(), username, new UserCounter(1, expirationTime));
        log.info("SAVE {},{},{}", httpMethod, username, expirationTime);
    }

    @Override
    public void incrementAndUpdate(HttpMethod httpMethod, String username, UserCounter userCounter) {
        int counter = userCounter.getCounter();
        long timeToExpired = userCounter.getTimeExpired();
        userCounterRepository.save(httpMethod.name(), username, new UserCounter(++counter, timeToExpired));
        log.info("UPDATE {},{},{}", httpMethod.name(), username, counter);
    }

    @Override
    public Optional<UserCounter> findUserCounter(HttpMethod httpMethod, String userName) {
        return userCounterRepository.findByUsername(httpMethod.name(), userName);
    }
}
