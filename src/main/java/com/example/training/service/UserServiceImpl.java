package com.example.training.service;

import com.example.training.model.User;
import com.example.training.repository.UserRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getById(Integer id) throws NotFoundException {
        log.info("UserServiceImpl.getById." + "id=" + id + " This  User id is found");
        return findById(id);
    }

    @Override
    public User save(User user) {
        log.info("UserServiceImpl.saveUser." + user.toString() + " created");
//        User userUpdate = findById(id);
        user.setFirstName(user.getFirstName());
        user.setLastName(user.getLastName());
        return userRepository.save(user);
    }

    @Override
    public List<User> getAll() {
        log.info("UserServiceImpl.getAllUser." );
        return userRepository.findAll();
    }

//    @Override
//    public User update(Integer id, User user) {
//        log.info("UserServiceImpl.updateUser." + "id=" + id + "user=" + user + " User is update");
//        User userUpdate = findById(id);
//        user.setFirstName(user.getFirstName());
//        user.setLastName(user.getLastName());
//        return userRepository.save(userUpdate);
//    }

    @Override
    public void delete(Integer id) throws NotFoundException {
        log.info("UserServiceImpl.deleteUser." + userRepository.getById(id) + ". User is deleted");
        userRepository.delete(findById(id));
    }


    private User findById(Integer id) throws NotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not exist with id= " + id));
    }

}
