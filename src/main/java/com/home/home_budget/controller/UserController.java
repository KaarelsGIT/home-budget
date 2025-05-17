package com.home.home_budget.controller;

import com.home.home_budget.dto.UserResponseDTO;
import com.home.home_budget.dto.UserUpdateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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

//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<UserResponseDTO> addUser(@RequestBody User user) {
        try {
            User createdUser = userService.createUser(user);
            return ResponseEntity.ok(new UserResponseDTO(createdUser));
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> viewUser(@PathVariable Long id) {
        try {
            Optional<User> user = userService.getUserById(id);
            return user
                    .map(u -> ResponseEntity.ok(new UserResponseDTO(u)))
                    .orElseGet(() -> ResponseEntity.notFound().build());

        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @RequestBody UserUpdateDTO dto) {
        try {
            Optional<User> existing = userService.getUserById(id);
            if (existing.isEmpty())
                return ResponseEntity.notFound().build();

            User updatedUser = userService.updateUser(existing.get(), dto);
            return ResponseEntity.ok(new UserResponseDTO(updatedUser));
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserResponseDTO>> getSortedAllUsers(@RequestParam(required = false) String sort) {
        try {
            List<User> users = userService.getSortedUsers(sort);
            List<UserResponseDTO> userDTOs = users.stream()
                    .map(UserResponseDTO::new)
                    .toList();
            return ResponseEntity.ok(userDTOs);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
