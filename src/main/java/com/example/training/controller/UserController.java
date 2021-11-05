package com.example.training.controller;

import com.example.training.model.User;
import com.example.training.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Optional<Integer>> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getById(id).map(User::getId));
    }

    @PutMapping("{id}")
    public ResponseEntity<Integer> put(@RequestBody User inputUser) {
        return ResponseEntity.ok(userService.put(inputUser).getId());
    }

    @GetMapping
    public List<User> getAll() {
        return userService.getAll();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Integer id) {
        userService.delete(id);
        return ResponseEntity.notFound().build();
    }
}
