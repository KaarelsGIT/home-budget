package com.home.home_budget.dto;

import com.home.home_budget.Model.Income;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Data
public class IncomesResponseDTO {

    private List<Income> incomes;
    private BigDecimal totalAmount;

}
