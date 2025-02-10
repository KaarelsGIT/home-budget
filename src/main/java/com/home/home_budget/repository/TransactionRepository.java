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

    Page<T> findByUserIdAndDateAndCategoryId(Long userId, LocalDate date, Long categoryId, Pageable pageable);
    Page<T> findByUserIdAndDate(Long userId, LocalDate date, Pageable pageable);
    Page<T> findByUserIdAndCategoryId(Long userId, Long categoryId, Pageable pageable);
    Page<T> findByDateAndCategoryId(LocalDate date, Long categoryId, Pageable pageable);
    Page<T> findByUserId(Long userId, Pageable pageable);
    Page<T> findByDate(LocalDate date, Pageable pageable);
    Page<T> findByCategoryId(Long categoryId, Pageable pageable);

    @Query("SELECT t FROM #{#entityName} t WHERE t.user.id = :userId AND FUNCTION('YEAR', t.date) = :year AND t.category.id = :categoryId")
    Page<T> findByUserIdAndYearAndCategoryId(@Param("userId") Long userId, @Param("year") Integer year, @Param("categoryId") Long categoryId, Pageable pageable);

    @Query("SELECT t FROM #{#entityName} t WHERE t.user.id = :userId AND FUNCTION('YEAR', t.date) = :year")
    Page<T> findByUserIdAndYear(@Param("userId") Long userId, @Param("year") Integer year, Pageable pageable);

    @Query("SELECT t FROM #{#entityName} t WHERE FUNCTION('YEAR', t.date) = :year AND t.category.id = :categoryId")
    Page<T> findByYearAndCategoryId(@Param("year") Integer year, @Param("categoryId") Long categoryId, Pageable pageable);

    @Query("SELECT t FROM #{#entityName} t WHERE FUNCTION('YEAR', t.date) = :year")
    Page<T> findByYear(@Param("year") Integer year, Pageable pageable);

    @Query("SELECT DISTINCT FUNCTION('YEAR', t.date) FROM #{#entityName} t ORDER BY FUNCTION('YEAR', t.date) DESC")
    List<Integer> findListOfYears();
}
