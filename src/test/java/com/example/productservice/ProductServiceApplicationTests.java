package com.example.productservice;

import com.example.productservice.models.Category;
import com.example.productservice.models.Product;
import com.example.productservice.repository.CategoryRepository;
import com.example.productservice.repository.ProductRepository;
import com.example.productservice.repository.projections.ProductTitleAndDescriptions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class ProductServiceApplicationTests {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

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

    @Test
    @Transactional // To run it in one go if it is using Lazy, as 2 queries are fired
    // In lazy the collection items are called only when it is called, example getProduct() query runs only on call
    // Default fetch type is Lazy
    // In order to make it Eager, just use Fetch type = Eager inside the main Model
    public void testFetchTypes(){
        Optional<Category> categoryOptional = categoryRepository.findById(1L);
        System.out.println(categoryOptional.get().getName());
        System.out.println(categoryOptional.get().getProducts());
    }

    //    nPlusOne problem arise when the queries are unnecessarily gets fired.
    //    In the below code there are three queries being fired:
    //    Hibernate: select c1_0.id,c1_0.created_at,c1_0.is_deleted,c1_0.name,c1_0.updated_at from category c1_0
    //    Hibernate: select p1_0.category_id,p1_0.id,p1_0.created_at,p1_0.description,p1_0.image_url,p1_0.is_deleted,p1_0.price,p1_0.title,p1_0.updated_at from product p1_0 where p1_0.category_id=?
    //    Hibernate: select p1_0.category_id,p1_0.id,p1_0.created_at,p1_0.description,p1_0.image_url,p1_0.is_deleted,p1_0.price,p1_0.title,p1_0.updated_at from product p1_0 where p1_0.category_id=?
    //    However i have only fired one and other are just taking its data.
    //    Solution: Make you own query in this case instead of using orm queries like findAll
    @Test
    @Transactional
    public void nPlusOneProblem (){
       List<Category> allCategoriesData = categoryRepository.findAll();
       for(Category category: allCategoriesData){
           for(Product product: category.getProducts()){
               System.out.println(product.getTitle());
           }
       }
    }





}
