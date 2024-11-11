package com.example.productservice.dtos;

import com.example.productservice.models.Category;
import com.example.productservice.models.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FakeStoreDTO {
    private Long id;
    private String title;
    private String description;
    private String price;
    private String image;
    private String category;

    public Product toProduct() {
        Product product = new Product();
        product.setId(getId());
        product.setTitle(getTitle());
        product.setDescription(getDescription());
        product.setPrice(Double.parseDouble(getPrice()));
        product.setImageUrl(getImage());

        Category category = new Category();
        category.setName(getCategory());
        product.setCategory(category);

        return product;
    }
}
