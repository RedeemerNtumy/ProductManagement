package com.example.products.service;

import com.example.products.model.Product;
import com.example.products.model.Subcategory;
import com.example.products.repository.CategoryRepository;
import com.example.products.repository.ProductRepository;
import com.example.products.repository.SubcategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private SubcategoryRepository subcategoryRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public Product addProduct(Long subcategoryId, String productName, Double productPrice, String productDescription) {
        Optional<Subcategory> subcategory = subcategoryRepository.findById(subcategoryId);
        if (subcategory.isPresent()) {
            Product product = new Product(productName, subcategory.get(),productPrice,productDescription);
            return productRepository.save(product);
        } else {
            throw new IllegalArgumentException("Subcategory not found");
        }
    }

    @Transactional
    public Product updateProduct(Long productId, String newName) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
            Product existingProduct = product.get();
            existingProduct.setName(newName);
            return productRepository.save(existingProduct);
        } else {
            throw new IllegalArgumentException("Product not found");
        }
    }

    @Transactional
    public void deleteProduct(Long productId) {
        if (productRepository.existsById(productId)) {
            productRepository.deleteById(productId);
        } else {
            throw new IllegalArgumentException("Product not found");
        }
    }

    public List<Product> getAllProductsBySubcategoryId(Long subcategoryId) {
        return productRepository.findAllBySubcategoryId(subcategoryId);
    }

    public List<Product> getAllProductsByCategoryId(Long categoryId) {
        return productRepository.findAllByCategoryId(categoryId);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
