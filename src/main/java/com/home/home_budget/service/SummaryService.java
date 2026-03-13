package com.home.home_budget.service;

import com.home.home_budget.Model.Category;
import com.home.home_budget.Model.Expense;
import com.home.home_budget.Model.Income;
import com.home.home_budget.Model.Transaction;
import com.home.home_budget.dto.BudgetSummaryDTO;
import com.home.home_budget.repository.ExpenseRepository;
import com.home.home_budget.repository.IncomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Month;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SummaryService {

    private final IncomeRepository incomeRepository;
    private final ExpenseRepository expenseRepository;

    private static final String[] MONTHS = {
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
    };

    public BudgetSummaryDTO getBudgetSummary(Long userId, Integer year, Integer month) {
        List<Income> incomes = incomeRepository.findFilteredTransactions(userId, year, month, null, null, Pageable.unpaged()).getContent();
        List<Expense> expenses = expenseRepository.findFilteredTransactions(userId, year, month, null, null, Pageable.unpaged()).getContent();

        Map<String, BudgetSummaryDTO.CategorySummaryDTO> incomeCategories = new HashMap<>();
        Map<String, BudgetSummaryDTO.CategorySummaryDTO> expenseCategories = new HashMap<>();
        Map<String, BudgetSummaryDTO.MonthlyTotalDTO> monthlyTotals = new LinkedHashMap<>();

        for (String m : MONTHS) {
            monthlyTotals.put(m, new BudgetSummaryDTO.MonthlyTotalDTO(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO));
        }

        BigDecimal totalIncome = BigDecimal.ZERO;
        BigDecimal totalExpense = BigDecimal.ZERO;

        for (Income income : incomes) {
            BigDecimal amount = income.getAmount();
            String categoryName = income.getCategory() != null ? income.getCategory().getName() : "Uncategorized";
            String m = MONTHS[income.getDate().getMonthValue() - 1];

            BudgetSummaryDTO.CategorySummaryDTO catSummary = incomeCategories.computeIfAbsent(categoryName, k -> createEmptyCategorySummary());
            catSummary.getMonthlyAmounts().put(m, catSummary.getMonthlyAmounts().get(m).add(amount));
            catSummary.setTotal(catSummary.getTotal().add(amount));

            BudgetSummaryDTO.MonthlyTotalDTO monthTotal = monthlyTotals.get(m);
            monthTotal.setIncome(monthTotal.getIncome().add(amount));
            monthTotal.setBalance(monthTotal.getIncome().subtract(monthTotal.getExpense()));

            totalIncome = totalIncome.add(amount);
        }

        for (Expense expense : expenses) {
            BigDecimal amount = expense.getAmount();
            String categoryName = expense.getCategory() != null ? expense.getCategory().getName() : "Uncategorized";
            String m = MONTHS[expense.getDate().getMonthValue() - 1];

            BudgetSummaryDTO.CategorySummaryDTO catSummary = expenseCategories.computeIfAbsent(categoryName, k -> createEmptyCategorySummary());
            catSummary.getMonthlyAmounts().put(m, catSummary.getMonthlyAmounts().get(m).add(amount));
            catSummary.setTotal(catSummary.getTotal().add(amount));

            BudgetSummaryDTO.MonthlyTotalDTO monthTotal = monthlyTotals.get(m);
            monthTotal.setExpense(monthTotal.getExpense().add(amount));
            monthTotal.setBalance(monthTotal.getIncome().subtract(monthTotal.getExpense()));

            totalExpense = totalExpense.add(amount);
        }

        return BudgetSummaryDTO.builder()
                .incomeCategories(incomeCategories)
                .expenseCategories(expenseCategories)
                .monthlyTotals(monthlyTotals)
                .totalIncome(totalIncome)
                .totalExpense(totalExpense)
                .totalBalance(totalIncome.subtract(totalExpense))
                .build();
    }

    private BudgetSummaryDTO.CategorySummaryDTO createEmptyCategorySummary() {
        Map<String, BigDecimal> monthlyAmounts = new HashMap<>();
        for (String m : MONTHS) {
            monthlyAmounts.put(m, BigDecimal.ZERO);
        }
        return new BudgetSummaryDTO.CategorySummaryDTO(monthlyAmounts, BigDecimal.ZERO);
    }
}
