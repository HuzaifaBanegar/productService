package com.example.productservice.services;

import com.example.productservice.errorHandlers.ProductNotFoundException;
import com.example.productservice.models.Category;
import com.example.productservice.models.Product;
import com.example.productservice.repository.CategoryRepository;
import com.example.productservice.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("databseProductService")
public class DatabaseService implements ProductService{

    private final CategoryRepository categoryRepository;
    private ProductRepository productRepository;
    public DatabaseService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product getProductDetails(Long id)  throws ProductNotFoundException {
       Optional<Product> prodOptional = productRepository.findById(id);
       if(prodOptional.isEmpty()){
           throw new ProductNotFoundException("Product Not Found");
       }

       return prodOptional.get();

    }

    @Override
    public Product createProduct(String title, String description, Double price, String category, String image) {
        Product inputProduct = new Product();
        inputProduct.setTitle(title);
        inputProduct.setDescription(description);
        inputProduct.setPrice(price);
        inputProduct.setImageUrl(image);

        //        Do not follow this convention:
        //        Category categoryObj = new Category();
        //        categoryObj.setName(category);
        //        inputProduct.setCategory(categoryObj);
        //        Do this instead:
        Category categoryFromRepo = categoryRepository.findByName(category);
        if(categoryFromRepo == null){
            System.out.println("Category not found, New Category created: "+ category);
            Category newCategory = new Category();
            newCategory.setName(category);
            categoryFromRepo = newCategory;
        }
        inputProduct.setCategory(categoryFromRepo);

        return productRepository.save(inputProduct);
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> productList = productRepository.findAllProducts();
        System.out.println("All Products: "+ productList);
        if(productList.isEmpty()){
            System.out.println("No products found");
        }
        return productList;
    }

    @Override
    public Product updateProduct(Long id, String title, String description, Double price, String category, String image) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        if (price == null || price <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0");
        }
        if (category == null || category.isEmpty()) {
            throw new IllegalArgumentException("Category cannot be null or empty");
        }

        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new EntityNotFoundException("Product with ID " + id + " not found");
        }

        Product productToUpdate = optionalProduct.get();
        productToUpdate.setTitle(title);
        productToUpdate.setDescription(description);
        productToUpdate.setPrice(price);
        productToUpdate.setImageUrl(image);

        Category categoryFromRepo = categoryRepository.findByName(category);
        if (categoryFromRepo == null) {
            categoryFromRepo = new Category();
            categoryFromRepo.setName(category);
            categoryRepository.save(categoryFromRepo);
        }
        productToUpdate.setCategory(categoryFromRepo);

        return productRepository.save(productToUpdate);
    }


    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if(categories.isEmpty()){
            System.out.println("Category not found");
        }
        return categories;
    }

    @Override
    public Product deleteProduct(Long id) {
        Product productToDelete = productRepository.findById(id).get();
        productRepository.delete(productToDelete);
        return productToDelete;
    }
}
