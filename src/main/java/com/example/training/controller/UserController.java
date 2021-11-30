package com.example.training.controller;

import com.example.training.model.UserEntity;
import com.example.training.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable Integer id) {
        return userService.getById(id).map(ResponseEntity::ok).orElse(ResponseEntity.badRequest().build());
    }

    @PutMapping
    public ResponseEntity<Integer> put(@RequestBody UserEntity userEntity) {
        return ResponseEntity.ok(userService.put(userEntity));
    }

    @GetMapping
    public List<UserEntity> getAll() {
        return userService.getAll();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        return userService.delete(id) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

}
