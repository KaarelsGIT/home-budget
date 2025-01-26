package com.home.home_budget.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.home.home_budget.Model.User;
import com.home.home_budget.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add")
    public String addUser(@RequestBody User user) {
        try {
            userService.createUser(user);
        } catch (Exception e) {
            return "Error creating user: " + e.getMessage();
        }
        return "User created successfully";
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            users = userService.getAllUsers();
        } catch (Exception e) {
            return null;
        }
        return users;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            userService.deleteUser(id);
            return ResponseEntity.ok().build();
        }
    }
    
}
