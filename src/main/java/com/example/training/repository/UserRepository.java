package com.example.training.repository;

import com.example.training.model.UserOriginal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<UserOriginal, Integer> {
    int deleteUserById(int id);

    Optional<UserOriginal> findOneByLastName(String lastName);
}
