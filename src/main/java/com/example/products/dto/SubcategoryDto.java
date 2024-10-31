package com.example.products.dto;

import com.example.products.model.Product;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubcategoryDto {

    private Long id;
    private String name;
    private CategoryDto category;
    private Set<Product> products = new HashSet<>();
}
