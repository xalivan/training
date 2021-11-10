package com.example.training.service;

import com.example.training.model.UserEntity;
import com.example.training.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Optional<UserEntity> getById(int id) {
        log.info("UserServiceImpl.getById." + "id=" + id + " This  User id is found");
        return userRepository.findById(id);
    }

    @Override
    public int put(UserEntity userEntity) {
        log.info("UserServiceImpl.saveUser." + userEntity.toString() + " created");
        return userRepository.save(userEntity).getId();
    }

    @Override
    public List<UserEntity> getAll() {
        log.info("UserServiceImpl.getAllUser.");
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public boolean delete(int id) {
        log.info("UserServiceImpl.delete. User with id= " + id + "deleted");
        return userRepository.deleteUserById(id) > 0;
    }

}
