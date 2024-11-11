package com.example.productservice.controller;

import com.example.productservice.dtos.CreateProductDTO;
import com.example.productservice.models.Product;
import com.example.productservice.services.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    private ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/products/{id}")
    public Product getProductsDetails(@PathVariable("id") Long id) {
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

}
//        tring description, Double price, String category, String image