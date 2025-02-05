package com.home.home_budget.controller;

import com.home.home_budget.Model.Transaction;
import com.home.home_budget.dto.TransactionResponseDTO;
import com.home.home_budget.service.CategoryService;
import com.home.home_budget.service.TransactionService;
import com.home.home_budget.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public abstract class TransactionController <T extends Transaction<T>> {

    private final TransactionService<T> service;
    private final UserService userService;
    private final CategoryService categoryService;

    public TransactionController(TransactionService<T> service,
                                 UserService userService,
                                 CategoryService categoryService) {
        this.service = service;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @PostMapping("/add")
    public ResponseEntity<T> addTransaction(@RequestBody T transaction) {
        return ResponseEntity.ok(service.saveTransaction(transaction));
    }

    @GetMapping("/{id}")
    public ResponseEntity<T> getTransactionById(@PathVariable Long id) {
        try {
            Optional<T> transaction = service.getTransactionById(id);
            if (transaction.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(transaction.get());
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<TransactionResponseDTO<T>> getFilteredAndSortedIncomes(
            @RequestParam(required = false) String sortBy, @RequestParam(required = false) String sortOrder,
            @RequestParam(required = false) Long userId, @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) LocalDate date, @RequestParam(required = false) Integer year,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size
    ) {
        try {
            Pageable pageable = PageRequest.of(page, size);

            Page<T> transactions = service.getFilteredAndSortedTransactions(userId, date, categoryId, year, sortBy, sortOrder, pageable);
            BigDecimal pageTotal = service.getpageTotalTransactionAmount(transactions);
            BigDecimal allTotal = service.getAllTotalFilteredTransactionAmount(userId, date, categoryId, year);

            //FOR TESTING TOTALS:
            System.out.println("pageTotal: " + pageTotal + " €");
            System.out.println("Total: " + allTotal + " €");

            TransactionResponseDTO<T> responseDTO = new TransactionResponseDTO<>(transactions, pageTotal, allTotal);
            return ResponseEntity.ok((responseDTO));
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<T> updateTransaction(@PathVariable Long id, @RequestBody T transaction) {
        try {
            Optional<T> existing = service.getTransactionById(id);
            if (existing.isEmpty())
                return ResponseEntity.notFound().build();

            T toUpdate = existing.get();
            toUpdate.setDate(transaction.getDate());
            toUpdate.setCategory(transaction.getCategory());
            toUpdate.setAmount(transaction.getAmount());
            toUpdate.setDescription(transaction.getDescription());
            toUpdate.setUser(transaction.getUser());

            T updated = service.saveTransaction(toUpdate);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity deleteTransaction(@PathVariable Long id) {
        try {
            Optional<T> toDelete = service.getTransactionById(id);
            if (toDelete.isEmpty())
                return ResponseEntity.notFound().build();

            service.deleteTransaction(id);
            return ResponseEntity.ok("Income deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting transaction");
        }
    }
}
