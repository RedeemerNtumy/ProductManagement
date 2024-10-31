package com.example.products.controller;

import com.example.products.dto.CategoryDto;
import com.example.products.dto.SubcategoryDto;
import com.example.products.model.Category;
import com.example.products.model.Subcategory;
import com.example.products.service.CategoryService;
import com.example.products.service.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    CategoryService categoryService;
    ProductService productService;

    public CategoryController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestParam String name) {
        try {
            CategoryDto category = categoryService.createCategory(name);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(category);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable("id") int id, @RequestBody Map<String, String> body) {
        try {
            String name = body.get("name");
            CategoryDto category = categoryService.getCategoryById(id);
            if (category != null) {
                category.setName(name);
                CategoryDto updatedCategory = categoryService.updateCategory(category);
                return ResponseEntity.ok(updatedCategory);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating category: " + e.getMessage());
        }
    }

    @PostMapping("/{categoryId}/subcategories")
    public ResponseEntity<Subcategory> addSubcategory(@PathVariable int categoryId, @RequestParam String name, HttpServletResponse response) {
        try {
            Subcategory subcategory = categoryService.addSubcategoryToCategory(categoryId, name);
            return ResponseEntity.ok(subcategory);
        } catch (IllegalStateException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return ResponseEntity.badRequest().body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryWithSubcategories(@PathVariable int categoryId) {
        try {
            CategoryDto category = categoryService.getCategoryById(categoryId);
            if (category != null) {
                return ResponseEntity.ok(category);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        try {
            List<CategoryDto> categories = categoryService.getAllCategories();
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/subcategories")
    public ResponseEntity<List<SubcategoryDto>> getAllSubcategories() {
        try {
            List<SubcategoryDto> subcategories = categoryService.getAllSubcategories();
            return ResponseEntity.ok(subcategories);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/{categoryId}/subcategories")
    public ResponseEntity<List<SubcategoryDto>> getSubcategoriesByCategory(@PathVariable int categoryId) {
        try {
            List<SubcategoryDto> subcategories = categoryService.getSubcategoriesByCategoryId(categoryId);
            if (subcategories.isEmpty()) {
                return ResponseEntity.noContent().build();  // No subcategories found
            }
            return ResponseEntity.ok(subcategories);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);  // Generic error handling
        }
    }
    @DeleteMapping("/{categoryId}/subcategories/{subcategoryId}")
    public ResponseEntity<?> deleteSubcategory(@PathVariable Long categoryId, @PathVariable Long subcategoryId) {
        try {
            categoryService.deleteSubcategory(subcategoryId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable int categoryId) {
        try {
            CategoryDto category = categoryService.getCategoryById(categoryId);
            if (category != null) {
                // Ensure the category does not have subcategories or products that depend on it
                if (category.hasSubCategory(category)) {
                    return ResponseEntity.badRequest().body("Cannot delete category because it has subcategories.");
                }
                categoryService.deleteCategory(categoryId);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting category: " + e.getMessage());
        }
    }
}
