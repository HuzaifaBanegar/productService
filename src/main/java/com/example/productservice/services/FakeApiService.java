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
import org.springframework.web.client.HttpClientErrorException;
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


    //PRODUCTS APIs------------------------------------------------------------------------------------------------

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


    @Override
    public Product updateProduct(Long id, String title, String description, Double price, String category, String image) {
        FakeStoreCreateDTO requestDTO = new FakeStoreCreateDTO();
        requestDTO.setTitle(title);
        requestDTO.setDescription(description);
        requestDTO.setPrice(price);
        requestDTO.setCategory(category);
        requestDTO.setImage(image);

        HttpEntity<FakeStoreCreateDTO> requestEntity = new HttpEntity<>(requestDTO);

        try{
            ResponseEntity<FakeStoreDTO> response = restTemplate.exchange(
                    "https://fakestoreapi.com/products/" + id,
                    HttpMethod.PATCH,
                    requestEntity,
                    FakeStoreDTO.class
            );

            if(response.getBody()==null){
                throw new RuntimeException("Failed to update product: No response body");
            }

            return response.getBody().toProduct();
        } catch (HttpClientErrorException e) {
            // Handle HTTP errors
            throw new RuntimeException("HTTP error while updating product: " + e.getStatusCode(), e);
        } catch (Exception e) {
            // Handle other exceptions
            throw new RuntimeException("Unexpected error while updating product", e);
        }

    }

    @Override
    public Product deleteProduct(Long id) throws ProductNotFoundException {
        final String URL = "https://fakestoreapi.com/products/" + id;

        try {
            HttpEntity<FakeStoreDTO> responseEntity = restTemplate.exchange(URL, HttpMethod.DELETE, null, FakeStoreDTO.class);
            FakeStoreDTO deletedProduct = responseEntity.getBody();

            if(deletedProduct == null){
                throw new ProductNotFoundException("Product Not Found");
            }

            return deletedProduct.toProduct();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    //CATEGORY APIs------------------------------------------------------------------------------------------------

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

    @Override
    public List<Product> getProductsByCategory(String category) throws ProductNotFoundException {
        final String URL = "https://fakestoreapi.com/products/category/" + category;
        try{
            ResponseEntity<FakeStoreDTO[]> responseEntity = restTemplate.exchange(
                    URL,
                    HttpMethod.GET,
                    null,
                    FakeStoreDTO[].class
            );

            FakeStoreDTO[] productList = responseEntity.getBody();

            if(productList == null){
                throw new ProductNotFoundException("Products Not Found");
            }else{
                List<Product> products = new ArrayList<>();
                for (FakeStoreDTO fakeStoreDTO : productList) {
                    products.add(fakeStoreDTO.toProduct());
                }
                return products;
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }
}
