package com.example.productservice.services;

import com.example.productservice.models.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FakeApiService implements ProductService{

    private RestTemplate restTemplate;
    public FakeApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getProductDetails(Long id) {
        return null;
    }
}
