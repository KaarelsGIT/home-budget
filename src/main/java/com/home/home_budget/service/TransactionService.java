package com.home.home_budget.service;

import com.home.home_budget.Model.Transaction;
import com.home.home_budget.repository.TransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService<T extends Transaction<T>> {

    private final TransactionRepository<T> repository;

    public TransactionService(TransactionRepository<T> repository) {
        this.repository = repository;
    }

   public T saveTransaction(T transaction) {
       return repository.save(transaction);
   }

    public void deleteTransaction(Long id) {
        repository.deleteById(id);
    }

    public Optional<T> getTransactionById(Long id) {
        return repository.findById(id);
    }

    //TODO: FIX BROKEN METHODS!
    public Page<T> getFilteredAndSortedTransactions(Long userId, LocalDate date, Long categoryId, Integer year,
                                                    String sortBy, String sortOrder, Pageable pageable) {
        if (sortBy == null)
            sortBy = "date";

        if (sortOrder == null)
            sortOrder = "desc";

        Sort.Direction direction;
        if (sortOrder != null && sortOrder.equalsIgnoreCase("desc")) {
            direction = Sort.Direction.DESC;
        } else {
            direction = Sort.Direction.ASC;
        }

        Sort sort = Sort.by(direction, sortBy);
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        if (year != null) {
            return getTransactionsFilteredByYear(userId, date, categoryId, year, sortedPageable);
        } else {
            return getTransactionsWithoutYearFilter(userId, date, categoryId, sortedPageable);
        }
    }

    private Page<T> getTransactionsFilteredByYear(Long userId, LocalDate date, Long categoryId, Integer year, Pageable pageable) {
        if (userId != null && date != null && categoryId != null) {
            return repository.findByUserIdAndYearAndCategoryId(userId, year, categoryId, pageable);
        } else if (userId != null && date != null) {
            return repository.findByUserIdAndYear(userId, year, pageable);
        } else if (userId != null && categoryId != null) {
            return repository.findByUserIdAndYearAndCategoryId(userId, year, categoryId, pageable);
        } else if (date != null && categoryId != null) {
            return repository.findByYearAndCategoryId(year, categoryId, pageable);
        } else if (userId != null) {
            return repository.findByUserIdAndYear(userId, year, pageable);
        } else if (date != null) {
            return repository.findByYear(year, pageable);
        } else if (categoryId != null) {
            return repository.findByYearAndCategoryId(year, categoryId, pageable);
        } else {
            return repository.findByYear(year, pageable);
        }
    }

    private Page<T> getTransactionsWithoutYearFilter(Long userId, LocalDate date, Long categoryId, Pageable pageable) {
        if (userId != null && date != null && categoryId != null) {
            return repository.findByUserIdAndDateAndCategoryId(userId, date, categoryId, pageable);
        } else if (userId != null && date != null) {
            return repository.findByUserIdAndDate(userId, date, pageable);
        } else if (userId != null && categoryId != null) {
            return repository.findByUserIdAndCategoryId(userId, categoryId, pageable);
        } else if (date != null && categoryId != null) {
            return repository.findByDateAndCategoryId(date, categoryId, pageable);
        } else if (userId != null) {
            return repository.findByUserId(userId, pageable);
        } else if (date != null) {
            return repository.findByDate(date, pageable);
        } else if (categoryId != null) {
            return repository.findByCategoryId(categoryId, pageable);
        } else {
            return repository.findAll(pageable);
        }
    }

    public BigDecimal getpageTotalTransactionAmount(Page<T> transactions) {
        return transactions.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getAllTotalFilteredTransactionAmount(Long userId, LocalDate date, Long categoryId, Integer year) {
        List<T> allFilteredTransactions;

        if (year != null) {
            allFilteredTransactions = repository.getListOfTransactionsFilteredByYear(userId, year, categoryId);
        } else {
            allFilteredTransactions = repository.getListOfTransactionsWithoutYearFilter(userId, date, categoryId);
        }


        return allFilteredTransactions.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}

