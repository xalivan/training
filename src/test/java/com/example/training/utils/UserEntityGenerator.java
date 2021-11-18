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

    public static UserEntity generatorUser() {
        return new UserEntity(new Random().nextInt( 1000), UUID.randomUUID().toString(), UUID.randomUUID().toString(), randomUUID().toString(), Role.USER);
    }

}
