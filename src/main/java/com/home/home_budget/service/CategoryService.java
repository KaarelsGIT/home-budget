package com.home.home_budget.service;

import com.home.home_budget.Model.Category;
import com.home.home_budget.Model.CategoryType;
import com.home.home_budget.repository.CategoryRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public List<Category> getFilteredAndSortedCategories (String type, String sortOrder) {
        if (sortOrder == null)
            sortOrder = "asc";

        Sort.Direction direction;
        if (sortOrder.equalsIgnoreCase("desc"))
            direction = Sort.Direction.DESC;
        else
            direction = Sort.Direction.ASC;

        Sort sort = Sort.by(direction, "name");

        if (type == null)
            return categoryRepository.findAll(sort);

        CategoryType categoryType;
        try {
            categoryType = CategoryType.valueOf(type.toUpperCase());
            return categoryRepository.findByType(categoryType, sort);
        } catch (IllegalArgumentException e) {
            return new ArrayList<>();
        }
    }

    public Category updateCategory(Category category) {
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
