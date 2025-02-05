package com.home.home_budget.controller;

import com.home.home_budget.Model.Income;
import com.home.home_budget.service.CategoryService;
import com.home.home_budget.service.IncomeService;
import com.home.home_budget.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/incomes")
public class IncomeController extends TransactionController<Income> {

    public IncomeController(IncomeService service) {
        super(service);
    }
}
