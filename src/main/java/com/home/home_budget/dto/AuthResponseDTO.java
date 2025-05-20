package com.home.home_budget.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDTO {
    private String token;
    private String role;
    private String message;

    public AuthResponseDTO(String message) {
        this.message = message;
    }
}
