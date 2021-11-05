package com.example.training.service;

import com.example.training.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    User getById(Integer id);
    void save(User user);
    List<User> getAll();
    User update(Integer id, User user);
    void delete(Integer id);
}
