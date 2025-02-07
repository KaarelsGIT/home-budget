package com.home.home_budget.repository;

import com.home.home_budget.Model.Category;
import com.home.home_budget.Model.CategoryType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String categoryName);
    Page<Category> findByType(CategoryType categoryType, Pageable pageable);
    Optional<Category> findByRecurringPayment(Boolean recurringPayment);
}
