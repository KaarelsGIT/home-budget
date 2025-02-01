package com.home.home_budget.controller;

import com.home.home_budget.Model.Income;
import com.home.home_budget.dto.IncomesResponseDTO;
import com.home.home_budget.service.IncomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/incomes")
public class IncomeController {

    private final IncomeService incomeService;

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
    public ResponseEntity<IncomesResponseDTO> getAllIncomes() {
        try {
            List<Income> incomes = incomeService.getAllIncomes();
            BigDecimal total = incomeService.getTotalIncome();
            IncomesResponseDTO response = new IncomesResponseDTO(incomes, total);
            return ResponseEntity.ok(response);
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
