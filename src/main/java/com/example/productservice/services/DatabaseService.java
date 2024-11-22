package com.example.productservice.services;

import com.example.productservice.errorHandlers.ProductNotFoundException;
import com.example.productservice.models.Category;
import com.example.productservice.models.Product;
import com.example.productservice.repository.CategoryRepository;
import com.example.productservice.repository.ProductRepository;
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
        return List.of();
    }

    @Override
    public Product updateProduct(Long id, String title, String description, Double price, String category, String image) {
        return null;
    }


    @Override
    public List<Category> getAllCategories() {
        return List.of();
    }
}
