package com.home.home_budget.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.home.home_budget.Model.User;
import com.home.home_budget.service.UserService;

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
public class UserController extends SharedData {

    protected UserController(UserService userService) {
        super(userService);
    }

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        try {
            User createdUser = userService.createUser(user);
            return ResponseEntity.ok(createdUser);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        try {
            Optional<User> existingUser = userService.getUserById(id);
            if (existingUser.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            User userToUpdate = existingUser.get();
            userToUpdate.setUsername(updatedUser.getUsername());

            User uppdatedUser = userService.updateUser(userToUpdate);
            return ResponseEntity.ok(uppdatedUser);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
       return super.getUsers();
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
