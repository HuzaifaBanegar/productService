package com.example.productservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchRequestDTO {
    public String query;
    public int pageNumber;
    public int pageSize;
}
