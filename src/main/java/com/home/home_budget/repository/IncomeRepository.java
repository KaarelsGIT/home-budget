package com.home.home_budget.repository;

import com.home.home_budget.Model.Category;
import com.home.home_budget.Model.Income;
import com.home.home_budget.Model.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, Long> {

    List<Income> findByUserAndDateAndCategoryId(User user, LocalDate date, Category category, Sort sort);

    List<Income> findByUserAndDate(User user, LocalDate date, Sort sort);

    List<Income> findByUserAndCategory(User user, Category category, Sort sort);

    List<Income> findByDateAndCategory(LocalDate date, Category category, Sort sort);

    List<Income> findByUser(User user, Sort sort);

    List<Income> findByDate(LocalDate date, Sort sort);

    List<Income> findByCategory(Category category, Sort sort);

    @Query("SELECT i FROM Income i WHERE i.user = :user AND EXTRACT(YEAR FROM i.date) = :year AND i.category = :category")
    List<Income> findByUserAndYearAndCategory(@Param("user") User user, @Param("year") Integer year, @Param("category") Category category, Sort sort);

    @Query("SELECT i FROM Income i WHERE i.user = :user AND EXTRACT(YEAR FROM i.date) = :year")
    List<Income> findByUserAndYear(@Param("user") User user, @Param("year") Integer year, Sort sort);

    @Query("SELECT i FROM Income i WHERE i.category = :category AND EXTRACT(YEAR FROM i.date) = :year")
    List<Income> findByCategoryAndYear(@Param("category") Category category, @Param("year") Integer year, Sort sort);

    @Query("SELECT i FROM Income i WHERE EXTRACT(YEAR FROM i.date) = :year")
    List<Income> findByYear(@Param("year") Integer year, Sort sort);
}
