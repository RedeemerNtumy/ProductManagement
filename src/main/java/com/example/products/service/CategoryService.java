package com.example.products.service;

import com.example.products.dto.CategoryDto;
import com.example.products.dto.SubcategoryDto;
import com.example.products.model.Category;
import com.example.products.model.Subcategory;
import com.example.products.repository.CategoryRepository;
import com.example.products.repository.SubcategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    CategoryRepository categoryRepository;
    SubcategoryRepository subcategoryRepository;

    public CategoryService(CategoryRepository categoryRepository, SubcategoryRepository subcategoryRepository) {
        this.categoryRepository = categoryRepository;
        this.subcategoryRepository = subcategoryRepository;
    }


    @Transactional
    public CategoryDto createCategory(String name) {
        Category category = new Category(name);
        categoryRepository.save(category);
        return buildCategoryDto(category);
    }

    @Transactional
    public CategoryDto updateCategory(CategoryDto category) {
        Category category1 = buildCategoryEntity(category);
        categoryRepository.save(category1);
        return buildCategoryDto(category1);
    }

    @Transactional
    public CategoryDto getCategoryById(int categoryId) {
        return categoryRepository.findById(categoryId)
                .map(this::buildCategoryDto)
                .orElse(null);
    }

    @Transactional
    public List<SubcategoryDto> getSubcategoriesByCategoryId(int categoryId) {
        return subcategoryRepository.findByCategoryId(categoryId)
                .stream().map(this::buildSubcategoryDto).collect(Collectors.toList());
    }

    @Transactional
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::buildCategoryDto).collect(Collectors.toList());
    }

    @Transactional
    public List<SubcategoryDto> getAllSubcategories() {
        return subcategoryRepository.findAll().stream()
                .map(this::buildSubcategoryDto).collect(Collectors.toList());
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
    public SubcategoryDto addSubcategoryToCategory(int categoryId, String subcategoryName) {
        Category category = categoryRepository.findById(categoryId).orElse(null);
        if (category != null) {
            Subcategory subcategory = new Subcategory(subcategoryName, category);
            subcategoryRepository.save(subcategory);
            boolean added = category.addSubcategory(subcategory);
            if (!added) {
                throw new IllegalStateException("Category can only have 2 subcategories.");
            }
            return SubcategoryDto.builder()
                    .name(subcategoryName)
                    .build();
        }
        throw new IllegalArgumentException("Category not found with ID: " + categoryId);
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

    private CategoryDto buildCategoryDto(Category category) {
       return CategoryDto.builder()
               .id(category.getId())
               .name(category.getName())
               .leftSubcategory(category.getLeftSubcategory() != null ?
                       buildSubcategoryDto(category.getLeftSubcategory()): null)
               .rightSubcategory(category.getRightSubcategory() != null ?
                       buildSubcategoryDto(category.getRightSubcategory()):null)
               .build();
    }

    private SubcategoryDto buildSubcategoryDto(Subcategory subcategory) {
        return SubcategoryDto.builder()
                .id(subcategory.getId())
                .name(subcategory.getName())
                .build();
    }
    private Category buildCategoryEntity(CategoryDto categoryDto) {
        Category category = new Category(categoryDto.getName());
        category.setId(categoryDto.getId());
        if (categoryDto.getLeftSubcategory() != null) {
            category.setLeftSubcategory(buildSubcategoryEntity(categoryDto.getLeftSubcategory()));
        }
        if (categoryDto.getRightSubcategory() != null) {
            category.setRightSubcategory(buildSubcategoryEntity(categoryDto.getRightSubcategory()));
        }
        return category;
    }

    private Subcategory buildSubcategoryEntity(SubcategoryDto subcategoryDto) {
        Subcategory subcategory = new Subcategory();
        subcategory.setId(subcategoryDto.getId());
        subcategory.setName(subcategoryDto.getName());
        return subcategory;
    }

}
