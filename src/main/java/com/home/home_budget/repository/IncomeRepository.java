package com.home.home_budget.repository;

import com.home.home_budget.Model.Income;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository("incomeRepository")
@Primary
public interface IncomeRepository extends TransactionRepository<Income> {
}
