package com.home.home_budget.repository;

import com.home.home_budget.Model.Category;
import com.home.home_budget.Model.CategoryType;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String categoryName);

    List<Category> findByType(CategoryType categoryType, Sort sort);
}
