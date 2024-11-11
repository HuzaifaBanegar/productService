package com.example.productservice.services;

import com.example.productservice.dtos.CreateProductDTO;
import com.example.productservice.models.Product;

import java.util.List;

public interface ProductService {
    public Product getProductDetails(Long id);
    public Product createProduct(String title, String description, Double price, String category, String image);
    public List<Product> getAllProducts();
}
