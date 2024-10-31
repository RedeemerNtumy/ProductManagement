package com.example.products.dto;

import com.example.products.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubcategoryDto {

    private Long id;
    private String name;
    private CategoryDto category;
    private Set<Product> products = new HashSet<>();
}
