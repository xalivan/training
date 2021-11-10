package com.example.training.service;

import com.example.training.model.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<UserEntity> getById(int id);

    int put(UserEntity userEntity);

    List<UserEntity> getAll();

    boolean delete(int id);
}
