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
     final static Random GENERATOR = new Random();

    private static Role getRole(){
        Role[] roles = Role.values();
        return roles[GENERATOR.nextInt(roles.length)];
    }

    public static UserEntity generateUser() {
        return new UserEntity(GENERATOR.nextInt(1000), UUID.randomUUID().toString(), UUID.randomUUID().toString(), randomUUID().toString(), getRole());
    }

}
