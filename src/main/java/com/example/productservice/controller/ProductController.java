package com.example.productservice.controller;

import com.example.productservice.models.Product;
import com.example.productservice.services.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    private ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public Product getAllProducts() {
        return null;
    }

    @GetMapping("/products/{id}")
    public Product getProductsDetails(@PathVariable("id") Long id) {
        return null;
    }

    @PostMapping("/products")
    public void createProduct(Product product) {

    }

}
