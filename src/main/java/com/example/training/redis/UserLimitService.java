package com.example.training.redis;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;


@Service
@AllArgsConstructor
public class UserLimitService {
    private final UserLimitRepository repository;

    public void save(String username, int counterGet, int counterPut) {
        repository.save(username, new UserLimit(counterGet, counterPut, LocalDate.now().plusDays(1)));
    }

    public Optional<UserLimit> find(String username) {
        return repository.findByUsername(username);
    }

    public void set(String username, int counterGet, int counterPut, LocalDate localDate) {
        repository.save(username, new UserLimit(counterGet, counterPut, localDate));
    }
}

