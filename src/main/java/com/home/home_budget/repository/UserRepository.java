package com.home.home_budget.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.home.home_budget.Model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

}
