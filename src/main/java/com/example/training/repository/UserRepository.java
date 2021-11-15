package com.example.training.repository;

import com.example.training.model.UserEntity;

import java.util.List;
import java.util.Optional;


public interface UserRepository {
    int insert(UserEntity user);

    int update(UserEntity user);

    int delete(int id);

    List<UserEntity> findAll();

    Optional<UserEntity> findById(int id);

    Optional<UserEntity> findOneByLastName(String lastName);
}
