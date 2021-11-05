package com.example.training.service;

import com.example.training.model.User;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    User getById(Integer id) throws NotFoundException;
    User save(User user);
    List<User> getAll();
//    User update(Integer id, User user);
    void delete(Integer id) throws NotFoundException;
}
