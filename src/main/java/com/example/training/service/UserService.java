package com.example.training.service;

import com.example.training.model.User;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    Optional<User> getById(Integer id) ;
    User save(User user);
    List<User> getAll();
    boolean delete(Integer id) ;
}
