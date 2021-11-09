package com.example.training.repository;

import com.example.training.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer> {
    int deleteUserById(int id);

    Optional<User> findOneByLastName(String lastName);
}
