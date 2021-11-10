package com.example.training.repository;

import com.example.training.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    int deleteUserById(int id);

    Optional<UserEntity> findOneByLastName(String lastName);
}
