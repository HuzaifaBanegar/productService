package com.example.productservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

@Getter
@Setter
@Entity
public class Category extends BaseModel{
    private String name;
    @OneToMany( mappedBy = "category", fetch = FetchType.EAGER) // Making fetch type Eager, by default it is Lazy
    @JsonIgnore
    @Fetch(FetchMode.JOIN) // Can be Select, Join , or SubSelect
    private List<Product> products;

    @Override
    public String toString(){
        return "{ name: "+ name +" }";
    }
}
