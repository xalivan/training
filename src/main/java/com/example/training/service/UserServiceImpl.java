package com.example.training.service;

import com.example.training.model.User;
import com.example.training.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Optional<User> getById(Integer id) {
        log.info("UserServiceImpl.getById." + "id=" + id + " This  User id is found");
        return findById(id);
    }

    @Override
    public User save(User user) {
        log.info("UserServiceImpl.saveUser." + user.toString() + " created");
        user.setFirstName(user.getFirstName());
        user.setLastName(user.getLastName());
        return userRepository.save(user);
    }

    @Override
    public List<User> getAll() {
        log.info("UserServiceImpl.getAllUser.");
        return userRepository.findAll();
    }

    @Override
    public boolean delete(Integer id) {
        log.info("UserServiceImpl.delete. User with id= " + id + "deleted");
        return userRepository.deleteUserById(id) > 0;
    }

    private Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

}
