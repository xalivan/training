package com.example.training.redis;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class UserCounterService {
    public static final int LIMIT_GET = 50;
    public static final int LIMIT_PUT = 5;
    public static final long TIME_EXPIRED = 86400000;
    private static final String GET = "GET";
    private static final String PUT = "PUT";
    private final UserCounterRepository repository;

    public void save(String httpMethod, String username) {
        long timeExpired = ZonedDateTime.now(ZoneOffset.UTC).toInstant().toEpochMilli() + TIME_EXPIRED;
        log.info("SAVE {},{},{}", httpMethod, username, timeExpired);
        repository.save(getHttpMethod(httpMethod), username, new UserCounter(1, timeExpired));
    }

    public Optional<UserCounter> findUser(String httpMethod, String username) {
        return repository.findByUsername(getHttpMethod(httpMethod), username);
    }

    public void update(String httpMethod, String username) {
        Optional<UserCounter> userCounter = getUserCounter(httpMethod, username);
        assert userCounter.isPresent();
        long timeToExpired = userCounter.get().getTimeToExpired();
        String getMethod = getHttpMethod(httpMethod);
        if (ZonedDateTime.now(ZoneOffset.UTC).toInstant().toEpochMilli() < timeToExpired) {
            int counter = userCounter.get().getCounter() + 1;
            log.info("UPDATE {},{},{}", httpMethod, username, counter);
            repository.save(getMethod, username, new UserCounter(counter, timeToExpired));
        } else {
            log.info("UPDATE AFTER EXPIRED {},{}", httpMethod, username);
            save(getMethod, username);
        }
    }

    private Optional<UserCounter> getUserCounter(String httpMethod, String username) {
        return repository.findByUsername(httpMethod, username).isPresent()
                ? repository.findByUsername(httpMethod, username)
                : Optional.empty();
    }

    private String getHttpMethod(String httpMethod) {
        return httpMethod.equals("GET") ? GET : PUT;
    }
}
