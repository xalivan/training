package com.example.training.redis.service;

import com.example.training.redis.dao.UserCounterRepositoryImpl;
import com.example.training.redis.model.UserCounter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class UserCounterServiceImpl implements UserCounterService {
    private final long timeExpiredInMinutes;
    private final String methodGet;
    private final String methodPut;
    private final Integer limitGet;
    private final Integer limitPut;
    private final UserCounterRepositoryImpl repository;

    public UserCounterServiceImpl(@Value("${timeExpiredInMinutes}") long timeExpiredInMinutes,
                                  @Value("${methodGet}") String methodGet,
                                  @Value("${methodPut}") String methodPut,
                                  @Value("${limitGet}") Integer limitGet,
                                  @Value("${limitPut}") Integer limitPut,
                                  UserCounterRepositoryImpl repository) {
        this.timeExpiredInMinutes = timeExpiredInMinutes;
        this.methodGet = methodGet;
        this.methodPut = methodPut;
        this.limitGet = limitGet;
        this.limitPut = limitPut;
        this.repository = repository;
    }

    @Override
    public boolean saveOrIncrement(String httpMethod, String username) {
        Optional<UserCounter> userCounter = getUserCounter(httpMethod, username);
        if (userCounter.isEmpty() || isTimeExpired(userCounter.get().getTimeExpired())) {
            long timeStart = ZonedDateTime.now(ZoneOffset.UTC).toInstant().toEpochMilli() + timeExpiredInMinutes * 60 * 1000;
            log.info("SAVE {},{},{}", httpMethod, username, timeStart);
            repository.save(httpMethod, username, new UserCounter(1, timeStart));
        } else {
            int counter = userCounter.get().getCounter();
            long timeToExpired = userCounter.get().getTimeExpired();
            if (counter < getMethodsMap().get(httpMethod)) {
                repository.save(httpMethod, username, new UserCounter(counter += 1, timeToExpired));
                log.info("UPDATE {},{},{}", httpMethod, username, counter);
            } else {
                return false;
            }
        }
        return true;
    }

    @Override
    public Optional<UserCounter> findUser(String httpMethod, String username) {
        return repository.findByUsername(httpMethod, username);
    }

    private Map<String, Integer> getMethodsMap() {
        Map<String, Integer> mapMethods = new HashMap<>();
        mapMethods.put(methodGet, limitGet);
        mapMethods.put(methodPut, limitPut);
        return mapMethods;
    }

    private Optional<UserCounter> getUserCounter(String httpMethod, String username) {
        return repository.findByUsername(httpMethod, username);
    }

    private boolean isTimeExpired(long userTime) {
        return userTime - ZonedDateTime.now(ZoneOffset.UTC).toInstant().toEpochMilli() < 0;
    }
}
