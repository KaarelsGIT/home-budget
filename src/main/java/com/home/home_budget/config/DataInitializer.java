package com.home.home_budget.config;

import com.home.home_budget.Model.User;
import com.home.home_budget.Model.UserRole;
import com.home.home_budget.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initAdminUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            boolean adminExists = userRepository.findAll().stream()
                    .anyMatch(user -> user.getRole() == UserRole.ROLE_ADMIN);

            if (!adminExists) {
                User admin = new User();
                admin.setUsername("Kaarel");
                admin.setPassword(passwordEncoder.encode("admin"));
                admin.setRole(UserRole.ROLE_ADMIN);
                userRepository.save(admin);
                System.out.println("[DATA INITIALIZER] Admin user created: admin / admin");
            } else {
                System.out.println("[DATA INITIALIZER] Admin user already exists.");
            }
        };
    }
}
