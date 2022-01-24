package com.example.training.redis;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class UserCounter implements Serializable {
    private final int counter;
    private final long timeToExpired;
}
