package com.home.home_budget.repository;

import com.home.home_budget.Model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

@NoRepositoryBean
public interface TransactionRepository<T extends Transaction<T>> extends JpaRepository<T, Long> {

    @Query("SELECT DISTINCT FUNCTION('YEAR', t.date) FROM #{#entityName} t ORDER BY FUNCTION('YEAR', t.date) DESC")
    List<Integer> findListOfYears();

    @Query("SELECT t FROM #{#entityName} t " +
            "WHERE (:userId IS NULL OR t.user.id = :userId) " +
            "AND (:year IS NULL OR FUNCTION('YEAR', t.date) = :year) " +
            "AND (:month IS NULL OR FUNCTION('MONTH', t.date) = :month) " +
            "AND (:categoryId IS NULL OR t.category.id = :categoryId) " +
            "AND (:date IS NULL OR t.date = :date)")
    Page<T> findFilteredTransactions(
            @Param("userId") Long userId,
            @Param("year") Integer year,
            @Param("month") Integer month,
            @Param("categoryId") Long categoryId,
            @Param("date") LocalDate date,
            Pageable pageable);
}
