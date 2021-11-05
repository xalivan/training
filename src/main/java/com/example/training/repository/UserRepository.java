package com.example.training.repository;

import com.example.training.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {
        int deleteUserById(int id);
}
