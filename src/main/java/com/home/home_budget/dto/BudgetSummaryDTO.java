package com.home.home_budget.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BudgetSummaryDTO {
    private Map<String, CategorySummaryDTO> incomeCategories;
    private Map<String, CategorySummaryDTO> expenseCategories;
    private Map<String, MonthlyTotalDTO> monthlyTotals;
    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
    private BigDecimal totalBalance;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategorySummaryDTO {
        private Map<String, BigDecimal> monthlyAmounts;
        private BigDecimal total;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MonthlyTotalDTO {
        private BigDecimal income;
        private BigDecimal expense;
        private BigDecimal balance;
    }
}
