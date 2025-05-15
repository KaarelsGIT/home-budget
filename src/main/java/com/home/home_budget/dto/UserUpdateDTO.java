package com.home.home_budget.dto;

import com.home.home_budget.Model.UserRole;
import lombok.Data;

@Data
public class UserUpdateDTO {

    private String username;
    private UserRole role;
}
