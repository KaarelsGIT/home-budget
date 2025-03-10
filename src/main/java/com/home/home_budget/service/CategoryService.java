package com.home.home_budget.service;

import com.home.home_budget.Model.Category;
import com.home.home_budget.Model.CategoryType;
import com.home.home_budget.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public Optional<Category> getCategoryByName(String categoryName) {
        return categoryRepository.findByName(categoryName);
    }

    public Optional<Category> getCategoryByRecurringPayment(Boolean recurringPayment) {
        return categoryRepository.findByRecurringPayment(recurringPayment);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Page<Category> getFilteredAndSortedCategories (String type, String sortOrder, Pageable pageable) {
        if (sortOrder == null)
            sortOrder = "asc";

        Sort.Direction direction;
        if (sortOrder.equalsIgnoreCase("desc"))
            direction = Sort.Direction.DESC;
        else
            direction = Sort.Direction.ASC;

        Sort sort = Sort.by(direction, "name");
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        if (type == null)
            return categoryRepository.findAll(pageable);

        CategoryType categoryType;
        try {
            categoryType = CategoryType.valueOf(type.toUpperCase());
            return categoryRepository.findByType(categoryType, pageable);
        } catch (IllegalArgumentException e) {
            return Page.empty(pageable);
        }
    }

    public List<Category> getFilteredAndSortedCategoriesAsList(String type, String sortOrder) {
        if (sortOrder == null || sortOrder.isEmpty()) {
            sortOrder = "asc";
        }

        Sort.Direction direction = sortOrder.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;

        Sort sort = Sort.by(direction, "name");

        if (type == null || type.isEmpty()) {
            return categoryRepository.findAll(sort);
        }

        try {
            CategoryType categoryType = CategoryType.valueOf(type.toUpperCase());
            return categoryRepository.findByType(categoryType, sort);
        } catch (IllegalArgumentException e) {
            return List.of();
        }
    }


    public Category updateCategory(Category category) {
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
