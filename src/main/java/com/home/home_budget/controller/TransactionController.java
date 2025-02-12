package com.home.home_budget.controller;

import com.home.home_budget.Model.Transaction;
import com.home.home_budget.dto.TransactionResponseDTO;
import com.home.home_budget.service.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public abstract class TransactionController <T extends Transaction<T>> {

    private final TransactionService<T> service;

    public TransactionController(TransactionService<T> service) {
        this.service = service;
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

    @GetMapping("/years")
    public ResponseEntity<List<Integer>> getListOfYears() {
        try {
            List<Integer> years = service.getListOfYears();
            return ResponseEntity.ok(years);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<TransactionResponseDTO<T>> getFilteredAndSortedIncomes(
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortOrder,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) LocalDate date,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        System.out.println("received month: " + month);
        System.out.println("received year: " + year);
        System.out.println("received user: " + userId);
        System.out.println("received category: " + categoryId);
        System.out.println("received date: " + date);
        try {
            Pageable pageable = PageRequest.of(page, size);

            Page<T> transactions = service.getFilteredAndSortedTransactions(userId, date, categoryId, year, month, sortBy, sortOrder, pageable);
            BigDecimal pageTotal = service.getPageTotalTransactionAmount(transactions);
            BigDecimal allTotal = service.getAllTotalFilteredTransactionAmount(userId, date, categoryId, year, month, pageable);

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
