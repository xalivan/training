package com.example.training.controller;

import com.example.training.model.User;
import com.example.training.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    @GetMapping("{id}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable Integer id) {
        Optional<User> userOptional = userService.getById(id);
        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Integer> put(@RequestBody User inputUser) {
        User save = userService.save(inputUser);
        return ResponseEntity.ok(save.getId());
    }

    @GetMapping
    public List<User> getAll() {
        return userService.getAll();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Integer id) {
        userService.delete(id);
        return ResponseEntity.notFound().build();
    }
}
