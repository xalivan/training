package com.example.training.utils;

import com.example.training.model.Role;
import com.example.training.model.UserEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.Random;
import java.util.UUID;

import static java.util.UUID.randomUUID;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class UserEntityGenerator {
    private final static Random RANDOM = new Random();

    public static UserEntity generateUser() {
        return new UserEntity(RANDOM.nextInt(1000),
                              UUID.randomUUID().toString(),
                              UUID.randomUUID().toString(),
                              randomUUID().toString(), getRole());
    }

    private static Role getRole() {
        Role[] roles = Role.values();
        return roles[RANDOM.nextInt(roles.length)];
    }
}
