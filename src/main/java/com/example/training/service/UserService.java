package com.example.training.service;

import com.example.training.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> getById(int id) ;
    User put(User user);
    List<User> getAll();
    boolean delete(int id) ;
}
