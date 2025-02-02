package com.home.home_budget.dto;

import com.home.home_budget.Model.Income;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Data
public class IncomesResponseDTO {

    private Page<Income> incomes;
    private BigDecimal totalAmount;

}
