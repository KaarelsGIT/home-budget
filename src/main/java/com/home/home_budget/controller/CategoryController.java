package com.home.home_budget.controller;

import com.home.home_budget.Model.Category;
import com.home.home_budget.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

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

            return ResponseEntity.ok(category.get());
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        try {
            Optional<Category> existing = categoryService.getCategoryById(id);
            if (existing.isEmpty())
                return ResponseEntity.notFound().build();

            Category toUpdate = existing.get();
            toUpdate.setName(category.getName());
            toUpdate.setType(category.getType());
            toUpdate.setDescription(category.getDescription());

            Category updated = categoryService.updateCategory(toUpdate);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllFilteredAndSortedCategories(@RequestParam(required = false) String type,
                                                               @RequestParam(required = false) String sort,
                                                               @RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "10") int size,
                                                               @RequestParam(defaultValue = "false") boolean asList) {
        Pageable pageable = PageRequest.of(page, size);

        try {
            if (asList) {
                List<Category> listOfCategories = categoryService.getFilteredAndSortedCategoriesAsList(type, sort);
                return ResponseEntity.ok(listOfCategories);
            } else {
                Page<Category> categories = categoryService.getFilteredAndSortedCategories(type, sort, pageable);
                return ResponseEntity.ok(categories);
            }

        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteCategory(@PathVariable Long id) {
        try {
            Optional<Category> category = categoryService.getCategoryById(id);
            if (category.isEmpty())
                return ResponseEntity.notFound().build();

            categoryService.deleteCategory(id);
            return ResponseEntity.ok().body("Category deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

}
