package com.home.home_budget.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.home.home_budget.Model.User;
import com.home.home_budget.service.UserService;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        try {
            User createdUser = userService.createUser(user);
            return ResponseEntity.ok(createdUser);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> viewUser(@PathVariable Long id) {
        try {
            Optional<User> user = userService.getUserById(id);
            if (user.isEmpty())
                return ResponseEntity.notFound().build();

            return ResponseEntity.ok(user.get());
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
            Optional<User> existing = userService.getUserById(id);
            if (existing.isEmpty())
                return ResponseEntity.notFound().build();

            User toUpdate = existing.get();
            toUpdate.setUsername(user.getUsername());

            User uppdatedUser = userService.updateUser(toUpdate);
            return ResponseEntity.ok(uppdatedUser);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getSortedAllUsers(@RequestParam(required = false) String sort) {
        try {
            List<User> users = userService.getSortedUsers(sort);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        try {
            Optional<User> user = userService.getUserById(id);
            if (user.isEmpty())
                return ResponseEntity.notFound().build();

            userService.deleteUser(id);
            return ResponseEntity.ok().body("User deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

}
