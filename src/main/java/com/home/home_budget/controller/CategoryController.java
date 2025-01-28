package com.home.home_budget.controller;

import com.home.home_budget.Model.Category;
import com.home.home_budget.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    private CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("add")
    public ResponseEntity<Category> addCategory(@RequestBody Category category) {
        try {
            Category createdCategory = categoryService.createCategory(category);
            return ResponseEntity.ok(createdCategory);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> viewCategory(@PathVariable Long id) {
        try {
            Optional<Category> category = categoryService.getCategoryById(id);
            if (category.isEmpty())
                return ResponseEntity.notFound().build();
            else
                return ResponseEntity.ok(category.get());
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category modifiedCategory) {
        try {
            Optional<Category> existing = categoryService.getCategoryById(id);
            if (existing.isEmpty())
                return ResponseEntity.notFound().build();

            Category toUpdate = existing.get();
            toUpdate.setName(modifiedCategory.getName());
            toUpdate.setType(modifiedCategory.getType());
            toUpdate.setDescription(modifiedCategory.getDescription());

            Category updated = categoryService.updateCategory(toUpdate);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/all")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        Optional<Category> category = categoryService.getCategoryById(id);
        if (category.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            categoryService.deleteCategory(id);
            return ResponseEntity.ok().build();
        }
    }

}
