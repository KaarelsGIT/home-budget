package com.home.home_budget.dto;

import com.home.home_budget.Model.User;
import com.home.home_budget.Model.UserRole;
import lombok.Data;

@Data
public class UserResponseDTO {

    private Long id;
    private String username;
    private UserRole role;

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.role = user.getRole();
    }
}
