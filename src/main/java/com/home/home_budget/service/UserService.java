package com.home.home_budget.service;

import java.util.List;
import java.util.Optional;

import com.home.home_budget.dto.UserUpdateDTO;
import com.home.home_budget.repository.UserRepository;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.home.home_budget.Model.User;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getSortedUsers(String sortOrder) {
        if (sortOrder == null)
            sortOrder = "asc";

        Sort.Direction direction;
        if (sortOrder.equalsIgnoreCase("desc"))
            direction = Sort.Direction.DESC;
        else
           direction = Sort.Direction.ASC;

        Sort sort = Sort.by(direction, "username");

        return userRepository.findAll(sort);

    }

    public User updateUser(User existingUser, UserUpdateDTO dto) {
        existingUser.setUsername(dto.getUsername());
        existingUser.setRole(dto.getRole());

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            existingUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        return userRepository.save(existingUser);
    }

    public boolean userExists(Long id) {
        return userRepository.existsById(id);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
