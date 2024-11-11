package com.example.productservice.services;

import com.example.productservice.dtos.CreateProductDTO;
import com.example.productservice.dtos.FakeStoreCreateDto;
import com.example.productservice.dtos.FakeStoreDTO;
import com.example.productservice.models.Category;
import com.example.productservice.models.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeApiService implements ProductService{

    private RestTemplate restTemplate;
    public FakeApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getProductDetails(Long id) {
        FakeStoreDTO fakeStoreDTO = restTemplate.getForObject("https://fakestoreapi.com/products/"+ id , FakeStoreDTO.class);
        return fakeStoreDTO.toProduct();
    }

    @Override
    public Product createProduct(String title, String description, Double price, String category, String image) {
        FakeStoreCreateDto requestDTO = new FakeStoreCreateDto();
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
}
