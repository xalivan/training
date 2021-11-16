package com.example.training.model;

import lombok.*;

@Value
public class UserEntity {
    int id;
    String firstName;
    String lastName;
    String password;
    Role role;

}
