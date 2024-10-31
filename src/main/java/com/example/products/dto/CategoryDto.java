package com.example.products.dto;
import com.example.products.model.Category;
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

    public boolean hasSubCategory(CategoryDto category) {
        return (category.equals(rightSubcategory)) || (category.equals(leftSubcategory));
    }
}
