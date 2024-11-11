package com.example.productservice.dtos;

import com.example.productservice.models.Category;
import com.example.productservice.models.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductDTO {
    private String title;
    private String description;
    private double price;
    private String image;
    private String category;


}
