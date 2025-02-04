package com.home.home_budget.service;

import com.home.home_budget.Model.Expense;
import com.home.home_budget.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ExpenseService extends TransactionService<Expense> {

    public ExpenseService(@Qualifier("expenseRepository") ExpenseRepository repository) {
        super(repository);
    }
}
