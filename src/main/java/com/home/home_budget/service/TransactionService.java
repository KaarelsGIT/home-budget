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

    public Page<T> getFilteredAndSortedTransactions(Long userId,
                                                    LocalDate date,
                                                    Long categoryId,
                                                    Integer year,
                                                    Integer month,
                                                    String sortBy,
                                                    String sortOrder,
                                                    Pageable pageable) {
        if (sortBy == null) sortBy = "date";
        if (sortOrder == null) sortOrder = "desc";

        Sort.Direction direction = sortOrder.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        return repository.findFilteredTransactions(userId, year, month, categoryId, date, sortedPageable);
    }

    public BigDecimal getPageTotalTransactionAmount(Page<T> transactions) {
        return transactions.stream().map(Transaction::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getAllTotalFilteredTransactionAmount(Long userId,
                                                           LocalDate date,
                                                           Long categoryId,
                                                           Integer year,
                                                           Integer month,
                                                           Pageable pageable) {
        pageable = Pageable.unpaged();
        Page<T> allFilteredTransactions;
        allFilteredTransactions = repository.findFilteredTransactions(userId, year, month, categoryId, date, pageable);

        return allFilteredTransactions.stream().map(Transaction::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<Integer> getListOfYears() {
        return repository.findListOfYears();
    }
}

