package com.example.products.controller;

import com.example.products.model.Category;
import com.example.products.model.Subcategory;
import com.example.products.service.CategoryService;
import com.example.products.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;



    @PostMapping("/categories/{categoryId}/subcategories")
    public ResponseEntity<Subcategory> addSubcategory(@PathVariable int categoryId, @RequestParam String name) {
        try {
            Subcategory subcategory = productService.addSubcategoryToCategory(categoryId, name);
            return ResponseEntity.ok(subcategory);
        } catch (IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/subcategories/{subcategoryId}")
    public ResponseEntity<?> deleteSubcategory(@PathVariable long subcategoryId) {
        try {
            productService.deleteSubcategory(subcategoryId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
