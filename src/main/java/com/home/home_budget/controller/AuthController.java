package com.home.home_budget.controller;

import com.home.home_budget.Model.User;
import com.home.home_budget.dto.AuthRequestDTO;
import com.home.home_budget.dto.AuthResponseDTO;
import com.home.home_budget.service.JwtService;
import com.home.home_budget.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {


    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDTO authRequest) {
        var userOptional = userService.getUserByUsername(authRequest.getUsername());

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(401).body(new AuthResponseDTO("Kasutajat ei leitud"));
        }

        User user = userOptional.get();
        if (!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body(new AuthResponseDTO("Vale parool"));
        }

        String token = jwtService.generateToken(user);
        return ResponseEntity.ok(new AuthResponseDTO(token, user.getRole().name(), "Login edukas"));
    }
}
