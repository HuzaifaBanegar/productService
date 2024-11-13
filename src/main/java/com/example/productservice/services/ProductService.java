package com.example.productservice.services;

import com.example.productservice.errorHandlers.ProductNotFoundException;
import com.example.productservice.models.Category;
import com.example.productservice.models.Product;

import java.util.List;

public interface ProductService {
    public Product getProductDetails(Long id) throws ProductNotFoundException;
    public Product createProduct(String title, String description, Double price, String category, String image);
    public List<Product> getAllProducts();
    public List<Category> getAllCategories();
}
