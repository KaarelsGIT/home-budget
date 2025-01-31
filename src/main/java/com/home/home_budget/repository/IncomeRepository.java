package com.home.home_budget.repository;

import com.home.home_budget.Model.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IncomeRepository extends JpaRepository<Income, Long> {


    @Query(value = "select i from Income i where year(i.date) = :year")
    List<Income> findByYear(int year);

//    List<Income> findByMonth(String month);
//
//    List<Income> findByCategoryName(String category);
//
//    List<Income> findByYearAndMonth(int year, String month);
//
//    List<Income> findByYearAndCategory(int year, String category);
//
//    List<Income> findByMonthAndCategory(String monthName, String category);
//
//    List<Income> findByYearMonthAndCategory(int year, String month, String category);
}
