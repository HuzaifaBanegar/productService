package com.example.productservice.controller;

import com.example.productservice.dtos.CreateProductDTO;
import com.example.productservice.dtos.ErrorDTO;
import com.example.productservice.errorHandlers.ProductNotFoundException;
import com.example.productservice.models.Category;
import com.example.productservice.models.Product;
import com.example.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    private ProductService productService;
    public ProductController(@Qualifier("databseProductService") ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/products/{id}")
    public Product getProductsDetails(@PathVariable("id") Long id) throws ProductNotFoundException {
        return productService.getProductDetails(id);
    }

    @PostMapping("/products")
    public Product createProduct(@RequestBody CreateProductDTO requestDto) {
        return productService.createProduct(
                requestDto.getTitle(),
                requestDto.getDescription(),
                requestDto.getPrice(),
                requestDto.getCategory(),
                requestDto.getImage()
        );
    }

    @PatchMapping("/products/{id}")
    public Product editProduct(@PathVariable("id") Long id , @RequestBody CreateProductDTO requestDto ) {
        return productService.updateProduct(
                id,
                requestDto.getTitle(),
                requestDto.getDescription(),
                requestDto.getPrice(),
                requestDto.getCategory(),
                requestDto.getImage());
    }

    @GetMapping("/category")
    public List<Category> getAllCategories() {
        return productService.getAllCategories();
    }





}
//        String description, Double price, String category, String image