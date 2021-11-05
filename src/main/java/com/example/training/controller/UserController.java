package com.example.training.controller;

import com.example.training.model.User;
import com.example.training.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;



@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    @GetMapping("{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        return userService.getById(id).map(ResponseEntity::ok).orElse(ResponseEntity.badRequest().build());
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
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        return userService.delete(id) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }
}
