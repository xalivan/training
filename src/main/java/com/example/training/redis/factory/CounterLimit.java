package com.example.training.redis.factory;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class CounterLimit {
    private final int limit;

    public boolean isCounterLowerThanAllowedLimit(int counter) {
        return limit > counter;
    }
}
