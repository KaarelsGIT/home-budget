package com.home.home_budget.repository;

import com.home.home_budget.Model.Expense;
import org.springframework.stereotype.Repository;

@Repository("expenseRepository")
public interface ExpenseRepository extends TransactionRepository<Expense>{
}
