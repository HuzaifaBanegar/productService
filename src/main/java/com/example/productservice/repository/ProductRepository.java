package com.example.productservice.repository;

import com.example.productservice.models.Product;
import com.example.productservice.repository.projections.ProductTitleAndDescriptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    //ORM internally provides methods like these
    Product save(Product product);

    Optional<Product> findById(Long id);


    Product getProductByTitleAndCategory_name(String title, String categoryName);

    //HQL Query - Writing query in HQL way
    @Query("select p from Product p where p.category.id = :id")
    List<Product> getProductsByCategoryId(@Param("id") Long id);

    //SQL Query - Writing query in our old MySQL way
   @Query(value = "select p.title, p.description from product p where id= :id", nativeQuery = true)
   ProductTitleAndDescriptions getAllProductsTitleAndDescription(@Param("id") Long id);


}