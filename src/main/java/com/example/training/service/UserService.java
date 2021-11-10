package com.example.training.service;

import com.example.training.model.UserOriginal;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<UserOriginal> getById(int id);

    int put(UserOriginal userOriginal);

    List<UserOriginal> getAll();

    boolean delete(int id);
}
