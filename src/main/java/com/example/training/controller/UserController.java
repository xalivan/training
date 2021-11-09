package com.example.training.controller;

import com.example.training.model.User;
import com.example.training.security.UserDetailsImpl;
import com.example.training.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/success")
    public String getSuccessPage(Authentication authentication, Model model) {
        UserDetailsImpl details = (UserDetailsImpl) authentication.getPrincipal();
        String userRole = details.getAuthorities().toString();
        if (userRole.equals("[ADMIN]")) {
            return "HI ADMIN";
        }
        if (userRole.equals("[USER]")) {
            return "Hi USER";
        } else {
            return "redirect:/";
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Integer> put(@RequestBody User inputUser) {
        return ResponseEntity.ok(userService.put(inputUser));
    }

    @GetMapping
    public List<User> getAll() {

        return userService.getAll();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        return userService.delete(id) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

}
