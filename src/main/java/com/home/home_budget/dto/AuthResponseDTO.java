package com.home.home_budget.dto;

import lombok.Data;

@Data
public class AuthResponseDTO {
    private String message;

    public AuthResponseDTO(String message) {
        this.message = message;
    }
}
