package com.example.products.controller;

import com.example.products.model.Product;
import com.example.products.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestParam Long subcategoryId, @RequestParam String name, @RequestParam Double price, @RequestParam String description) {
        try {
            Product product = productService.addProduct(subcategoryId, name, price, description);
            return ResponseEntity.ok(product);
        } catch (DataAccessException e) {
            System.out.println(e+" Database exception");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (IllegalArgumentException e) {
            System.out.println(e+" Invalid Argument");
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            System.out.println(e+" An unexpected error occurred");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestParam String newName) {
        try {
            Product updatedProduct = productService.updateProduct(productId, newName);
            return ResponseEntity.ok(updatedProduct);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        try {
            productService.deleteProduct(productId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/subcategory/{subcategoryId}")
    public ResponseEntity<List<Product>> getProductsBySubcategory(@PathVariable Long subcategoryId) {
        List<Product> products = productService.getAllProductsBySubcategoryId(subcategoryId);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable Long categoryId) {
        List<Product> products = productService.getAllProductsByCategoryId(categoryId);
        return ResponseEntity.ok(products);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }
}
