package com.example.training.controller;

import com.example.training.model.UserOriginal;
import com.example.training.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    @GetMapping("{id}")
    public ResponseEntity<UserOriginal> getUserById(@PathVariable Integer id) {
        return userService.getById(id).map(ResponseEntity::ok).orElse(ResponseEntity.badRequest().build());
    }

    @PutMapping("{id}")
    public ResponseEntity<Integer> put(@RequestBody UserOriginal inputUserOriginal) {
        return ResponseEntity.ok(userService.put(inputUserOriginal));
    }

    @GetMapping("admin")
    public List<UserOriginal> getAllAdmin() {
        return userService.getAll();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        return userService.delete(id) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

}
