package com.example.productservice;

import com.example.productservice.models.Product;
import com.example.productservice.repository.ProductRepository;
import com.example.productservice.repository.projections.ProductTitleAndDescriptions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class ProductServiceApplicationTests {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void contextLoads() {
    }

    @Test
    public void test(){
        Optional<Product> productOptional = productRepository.findById(1L);
        Product product = productOptional.get();
        System.out.println(product);
    }

    @Test
    public void getByNameAndCategoryName(){
        Product product = productRepository.getProductByTitleAndCategory_name("Iphone 16 Pro", "smart-phones");
        System.out.println(product);
    }

    @Test
    public void getByCategoryId(){
        List<Product> products = productRepository.getProductsByCategoryId(1L);
        System.out.println(products);
    }

    @Test
    public void getAllProductsAndDescriptions(){
        ProductTitleAndDescriptions prodList = productRepository.getAllProductsTitleAndDescription(1L);
        System.out.println(prodList.getTitle()+", "+prodList.getDescription());
    }



}
