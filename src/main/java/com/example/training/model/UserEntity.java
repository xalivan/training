package com.example.training.model;

import lombok.*;

import java.util.concurrent.atomic.AtomicInteger;

@Value
public class UserEntity {
    int id;
    String firstName;
    String lastName;
    String password;
    Role role;


}
