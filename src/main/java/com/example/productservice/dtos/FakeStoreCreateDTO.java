package com.example.productservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FakeStoreCreateDTO {
    private String title;
    private String description;
    private double price;
    private String image;
    private String category;
}
