package com.example.products.service;

import com.example.products.model.Category;
import com.example.products.model.Subcategory;
import com.example.products.repository.CategoryRepository;
import com.example.products.repository.SubcategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SubcategoryRepository subcategoryRepository;

    @Transactional
    public Subcategory addSubcategoryToCategory(int categoryId, String subcategoryName) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            if (category.getSubcategories().size() >= 2) {
                throw new IllegalStateException("Maximum of 2 subcategories allowed per category.");
            }
            Subcategory subcategory = new Subcategory(subcategoryName, category);
            category.addSubcategory(subcategory);
            return subcategoryRepository.save(subcategory);
        } else {
            throw new IllegalArgumentException("Category not found with ID: " + categoryId);
        }
    }

    @Transactional
    public void deleteSubcategory(Long subcategoryId) {
        Optional<Subcategory> subcategoryOptional = subcategoryRepository.findById(subcategoryId);
        if (subcategoryOptional.isPresent()) {
            Subcategory subcategory = subcategoryOptional.get();
            Category category = subcategory.getCategory();
            category.removeSubcategory(subcategory);
            subcategoryRepository.delete(subcategory);
        } else {
            throw new IllegalArgumentException("Subcategory not found with ID: " + subcategoryId);
        }
    }
}
