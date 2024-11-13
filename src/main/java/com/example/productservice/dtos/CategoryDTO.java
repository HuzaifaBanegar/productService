package com.example.productservice.dtos;

import com.example.productservice.models.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO {
    private String name;

    public Category toCategory(){
        Category category = new Category();
        category.setName(name);
        return category;

    }

}
