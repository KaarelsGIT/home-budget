package com.home.home_budget.service;

import com.home.home_budget.Model.Income;
import com.home.home_budget.repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class IncomeService extends TransactionService<Income> {

    public IncomeService(@Qualifier("incomeRepository") IncomeRepository repository) {
        super(repository);
    }
}
