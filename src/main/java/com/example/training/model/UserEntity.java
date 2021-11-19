package com.example.training.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class UserEntity {
    int id;
    String firstName;
    String lastName;
    String password;
    Role role;

    @JsonCreator
    public UserEntity(@JsonProperty("id") int id,
                      @JsonProperty("firstName") String firstName,
                      @JsonProperty("lastName") String lastName,
                      @JsonProperty("password") String password,
                      @JsonProperty("role") Role role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.role = role;
    }
}
