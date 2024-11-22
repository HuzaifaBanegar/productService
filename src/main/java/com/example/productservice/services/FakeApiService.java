package com.example.productservice.services;

import com.example.productservice.dtos.CategoryDTO;
import com.example.productservice.dtos.FakeStoreCreateDTO;
import com.example.productservice.dtos.FakeStoreDTO;
import com.example.productservice.errorHandlers.ProductNotFoundException;
import com.example.productservice.models.Category;
import com.example.productservice.models.Product;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service("fakeAPIProductService")
public class FakeApiService implements ProductService{

    private RestTemplate restTemplate;
    public FakeApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getProductDetails(Long id) throws ProductNotFoundException{

        ResponseEntity<FakeStoreDTO> responseEntity =  restTemplate.getForEntity("https://fakestoreapi.com/products/"+ id , FakeStoreDTO.class);

        if(responseEntity.getStatusCode() == HttpStatusCode.valueOf(404)){
            //Do something
        }else if(responseEntity.getStatusCode() == HttpStatusCode.valueOf(500)){
            //Handle Exception
        }

        FakeStoreDTO fakeStoreDTO = responseEntity.getBody();
        if(fakeStoreDTO == null){
            throw new ProductNotFoundException("Product Not Found");
        }

        return fakeStoreDTO.toProduct();
    }

    @Override
    public Product createProduct(String title, String description, Double price, String category, String image) {
        FakeStoreCreateDTO requestDTO = new FakeStoreCreateDTO();
        requestDTO.setTitle(title);
        requestDTO.setDescription(description);
        requestDTO.setPrice(price);
        requestDTO.setCategory(category);
        requestDTO.setImage(image);

        FakeStoreDTO responseDto = restTemplate.postForObject("https://fakestoreapi.com/products", requestDTO, FakeStoreDTO.class);


        return responseDto.toProduct();

    }

    @Override
    public List<Product> getAllProducts() {
        FakeStoreDTO[] resposnseDto = restTemplate.getForObject("https://fakestoreapi.com/products/", FakeStoreDTO[].class);
        List<Product> products = new ArrayList<>();
        for (FakeStoreDTO fakeStoreDTO : resposnseDto) {
            products.add(fakeStoreDTO.toProduct());
        }

        return products;
    }

//    @Override
//    public Product updateProduct(Long id, String title, String description, Double price, String category, String image) {
//        return null;
//    }

    @Override
    public Product updateProduct(Long id, String title, String description, Double price, String category, String image) {
        FakeStoreCreateDTO requestDTO = new FakeStoreCreateDTO();
        requestDTO.setTitle(title);
        requestDTO.setDescription(description);
        requestDTO.setPrice(price);
        requestDTO.setCategory(category);
        requestDTO.setImage(image);

        HttpEntity<FakeStoreCreateDTO> requestEntity = new HttpEntity<>(requestDTO);
        ResponseEntity<FakeStoreDTO> response = restTemplate.exchange(
                "https://fakestoreapi.com/products/" + id,
                HttpMethod.PATCH,
                requestEntity,
                FakeStoreDTO.class
        );

        return response.getBody().toProduct();

    }



    @Override
    public List<Category> getAllCategories() {
        String[] categoryNames = restTemplate.getForObject("https://fakestoreapi.com/products/categories", String[].class);

        List<Category> categories = new ArrayList<>();
        for(String categoryName : categoryNames){
            CategoryDTO category = new CategoryDTO();
            category.setName(categoryName);
            categories.add(category.toCategory());
        }

        return categories;
    }
}
