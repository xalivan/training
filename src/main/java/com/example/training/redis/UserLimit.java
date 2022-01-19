package com.example.training.redis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
@ToString
public class UserLimit implements Serializable {
    private final int countMethodGet;
    private final int countMethodPut;
    private final LocalDate expired;
}
