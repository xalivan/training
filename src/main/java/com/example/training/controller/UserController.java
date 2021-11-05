package com.example.training.controller;

import com.example.training.model.User;
import com.example.training.service.UserServiceImpl;
import javassist.NotFoundException;
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
    public ResponseEntity<User> getUserById(@PathVariable Integer id) throws NotFoundException {
        return ResponseEntity.ok(userService.getById(id));
    }

//    @PostMapping
//    public ResponseEntity<Integer> createUser(@RequestBody User user) {
//        userService.save(user);
//        return ResponseEntity.ok(user.getId());
//    }
        @PutMapping("{id}")
    public ResponseEntity<Integer> put(@RequestBody User inputUser) {
            User save = userService.save(inputUser);
            return ResponseEntity.ok(save.getId());
    }

    @GetMapping
    public List<User> getAll() {
        return userService.getAll();
    }

//    @PutMapping("{id}")
//    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User inputUser) {
//        return ResponseEntity.ok(userService.update(id, inputUser));
//    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Integer id) throws NotFoundException {
        userService.delete(id);
        return ResponseEntity.ok(HttpStatus.NOT_FOUND);
    }


}
