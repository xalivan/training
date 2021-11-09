package com.example.training.controller;

import com.example.training.model.User;
import com.example.training.security.UserDetailsImpl;
import com.example.training.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users/")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    @GetMapping("{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        return userService.getById(id).map(ResponseEntity::ok).orElse(ResponseEntity.badRequest().build());
    }

    @PutMapping("{id}")
    public ResponseEntity<Integer> put(@RequestBody User inputUser) {
        return ResponseEntity.ok(userService.put(inputUser));
    }

    @GetMapping("admin")
    public List<User> getAllAdmin() {
        return userService.getAll();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        return userService.delete(id) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

}
