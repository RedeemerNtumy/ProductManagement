package com.example.products.dto;

import com.example.products.model.Category;
import com.example.products.model.Subcategory;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CategoryDto {

    private Long id;
    private String name;
    private SubcategoryDto leftSubcategory;
    private SubcategoryDto rightSubcategory;
}
