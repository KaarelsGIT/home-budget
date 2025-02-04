package com.home.home_budget.repository;

import com.home.home_budget.Model.Expense;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository("expenseRepository")
@Primary
public interface ExpenseRepository extends TransactionRepository<Expense>{
}
