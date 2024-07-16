package com.example.products.service;

import com.example.products.model.Category;
import com.example.products.model.Subcategory;
import com.example.products.repository.CategoryRepository;
import com.example.products.repository.SubcategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    CategoryRepository categoryRepository;
    SubcategoryRepository subcategoryRepository;

    public CategoryService(CategoryRepository categoryRepository, SubcategoryRepository subcategoryRepository) {
        this.categoryRepository = categoryRepository;
        this.subcategoryRepository = subcategoryRepository;
    }


    @Transactional
    public Category createCategory(String name) {
        Category category = new Category(name);
        return categoryRepository.save(category);
    }

    @Transactional
    public Category updateCategory(Category category) {
        return categoryRepository.save(category);
    }
    @Transactional
    public Category getCategoryById(int categoryId) {
        return categoryRepository.findById(categoryId).orElse(null);
    }

    @Transactional
    public List<Subcategory> getSubcategoriesByCategoryId(int categoryId) {
        return subcategoryRepository.findByCategoryId(categoryId);  // Assuming you have a method in your repository to find by category ID
    }

    @Transactional
    public List<Category> getAllCategories() {
        return categoryRepository.findAll(); // Using JpaRepository's findAll() method
    }
    @Transactional
    public List<Subcategory> getAllSubcategories() {
        return subcategoryRepository.findAll(); // Assuming you have a subcategoryRepository
    }

    @Transactional
    public void deleteCategory(int categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isPresent()) {
            Category foundCategory = category.get();
            // Ensure to clean up subcategories if they exist
            if (foundCategory.getLeftSubcategory() != null) {
                foundCategory.setLeftSubcategory(null);
            }
            if (foundCategory.getRightSubcategory() != null) {
                foundCategory.setRightSubcategory(null);
            }
            categoryRepository.delete(foundCategory);
        } else {
            throw new IllegalArgumentException("Category not found with ID: " + categoryId);
        }
    }
    @Transactional
    public Subcategory addSubcategoryToCategory(int categoryId, String subcategoryName) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            Subcategory subcategory = new Subcategory(subcategoryName, category);

            // Try to add the subcategory as either left or right child
            boolean added = category.addSubcategory(subcategory);
            if (!added) {
                throw new IllegalStateException("Category can only have 2 subcategories.");
            }

            subcategoryRepository.save(subcategory);
            return subcategory;
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

            // Determine if the subcategory is the left or right child and remove accordingly
            if (category.getLeftSubcategory() != null && category.getLeftSubcategory().getId().equals(subcategoryId)) {
                category.setLeftSubcategory(null);
            } else if (category.getRightSubcategory() != null && category.getRightSubcategory().getId().equals(subcategoryId)) {
                category.setRightSubcategory(null);
            }

            subcategoryRepository.delete(subcategory);
        } else {
            throw new IllegalArgumentException("Subcategory not found with ID: " + subcategoryId);
        }
    }
}
