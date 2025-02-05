package com.home.home_budget.controller;

import com.home.home_budget.Model.Expense;
import com.home.home_budget.service.CategoryService;
import com.home.home_budget.service.ExpenseService;
import com.home.home_budget.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController extends TransactionController<Expense> {

    public ExpenseController(ExpenseService service) {
        super(service);
    }
}
