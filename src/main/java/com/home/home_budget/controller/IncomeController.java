package com.home.home_budget.controller;

import com.home.home_budget.Model.Category;
import com.home.home_budget.Model.Income;
import com.home.home_budget.Model.User;
import com.home.home_budget.dto.IncomesResponseDTO;
import com.home.home_budget.service.CategoryService;
import com.home.home_budget.service.IncomeService;
import com.home.home_budget.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/incomes")
public class IncomeController {

    private final IncomeService incomeService;
    private final UserService userService;
    private final CategoryService categoryService;

    public IncomeController(IncomeService incomeService,
                            UserService userService,
                            CategoryService categoryService) {
        this.incomeService = incomeService;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @PostMapping("/add")
    public ResponseEntity<Income> addIncome(@RequestBody Income income) {
        try {
            Income createdIncome = incomeService.addIncome(income);
            return ResponseEntity.ok(createdIncome);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Income> viewIncome(@PathVariable Long id) {
        try {
            Optional<Income> income = incomeService.getIncomeById(id);
            if (income.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(income.get());
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<IncomesResponseDTO> getFilteredAndSortedIncomes(
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortOrder,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) LocalDate date,
            @RequestParam(required = false) Integer year) {

        try {
            User user = null;
            Category category = null;

            if (userId != null) {
                Optional<User> optionalUser = userService.getUserById(userId);
                if (optionalUser.isPresent())
                    user = optionalUser.get();
            }

            if (categoryId != null) {
                Optional<Category> optionalCategory = categoryService.getCategoryById(categoryId);
                if (optionalCategory.isPresent())
                    category = optionalCategory.get();
            }

            List<Income> incomes = incomeService.getFilteredAndSortedIncomes(user, date, category, sortBy, sortOrder, year);
            BigDecimal total = incomeService.getTotalIncomeAmount(incomes);
            IncomesResponseDTO response = new IncomesResponseDTO(incomes, total);
            return ResponseEntity.ok((response));
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Income> updateIncome(@PathVariable Long id, @RequestBody Income income) {
        try {
            Optional<Income> existing = incomeService.getIncomeById(id);
            if (existing.isEmpty())
                return ResponseEntity.notFound().build();

            Income toUpdate = existing.get();
            toUpdate.setDate(income.getDate());
            toUpdate.setCategory(income.getCategory());
            toUpdate.setAmount(income.getAmount());
            toUpdate.setDescription(income.getDescription());
            toUpdate.setUser(income.getUser());
            Income updated = incomeService.updateIncome(toUpdate);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity deleteIncome(@PathVariable Long id) {
        try {
            Optional<Income> toDelete = incomeService.getIncomeById(id);
            if (toDelete.isEmpty())
                return ResponseEntity.notFound().build();

            incomeService.deleteIncome(id);
            return ResponseEntity.ok("Income deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
