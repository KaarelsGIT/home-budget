package com.home.home_budget.repository;

import com.home.home_budget.Model.Category;
import com.home.home_budget.Model.Income;
import com.home.home_budget.Model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
public interface IncomeRepository extends JpaRepository<Income, Long> {

    Page<Income> findByUserAndDateAndCategoryId(User user, LocalDate date, Category category, Pageable pageable);

    Page<Income> findByUserAndDate(User user, LocalDate date, Pageable pageable);

    Page<Income> findByUserAndCategory(User user, Category category, Pageable pageable);

    Page<Income> findByDateAndCategory(LocalDate date, Category category, Pageable pageable);

    Page<Income> findByUser(User user, Pageable pageable);

    Page<Income> findByDate(LocalDate date, Pageable pageable);

    Page<Income> findByCategory(Category category, Pageable pageable);

    @Query("SELECT i FROM Income i WHERE i.user = :user AND EXTRACT(YEAR FROM i.date) = :year AND i.category = :category")
    Page<Income> findByUserAndYearAndCategory(@Param("user") User user,
                                              @Param("year") Integer year,
                                              @Param("category") Category category,
                                              Pageable pageable);

    @Query("SELECT i FROM Income i WHERE i.user = :user AND EXTRACT(YEAR FROM i.date) = :year")
    Page<Income> findByUserAndYear(@Param("user") User user,
                                   @Param("year") Integer year,
                                   Pageable pageable);

    @Query("SELECT i FROM Income i WHERE i.category = :category AND EXTRACT(YEAR FROM i.date) = :year")
    Page<Income> findByCategoryAndYear(@Param("category") Category category,
                                       @Param("year") Integer year,
                                       Pageable pageable);

    @Query("SELECT i FROM Income i WHERE EXTRACT(YEAR FROM i.date) = :year")
    Page<Income> findByYear(@Param("year") Integer year, Pageable pageable);

    @Query("SELECT COALESCE(SUM(i.amount), 0) FROM Income i WHERE (:user IS NULL OR i.user = :user) AND (:category IS NULL OR i.category = :category) AND (:date IS NULL OR i.date = :date) AND (:year IS NULL OR EXTRACT(YEAR FROM i.date) = :year)")
    BigDecimal getTotalFilteredIncome(@Param("user") User user,
                                      @Param("category") Category category,
                                      @Param("date") LocalDate date,
                                      @Param("year") Integer year);
}
