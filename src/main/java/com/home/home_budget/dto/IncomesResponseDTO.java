package com.home.home_budget.dto;

import com.home.home_budget.Model.Income;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class IncomesResponseDTO {

    private Page<Income> incomePage;
    private BigDecimal totalAmount;

}
