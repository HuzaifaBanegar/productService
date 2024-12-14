package com.example.productservice.repository;

import com.example.productservice.models.Product;
import com.example.productservice.repository.projections.ProductTitleAndDescriptions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query(value = "select * from product", nativeQuery = true)
    List<Product> findAllProducts();


    //HQL Query - Writing query in HQL way
//    @Query("select p from Product p where p.category.id = :id")
//    List<Product> getProductsByCategoryId(@Param("id") Long id);

    //SQL Query - Writing query in our old MySQL way
   @Query(value = "select p.title, p.description from product p where id= :id", nativeQuery = true)
   ProductTitleAndDescriptions getAllProductsTitleAndDescription(@Param("id") Long id);

    Page<Product> findByTitleContaining(String query, Pageable pageable);

    //HQL Query
    @Query("SELECT p FROM Product p WHERE p.category.name = :query")
    List<Product> getAllProductsByCategory(@Param("query") String query);

}