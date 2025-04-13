package com.webapp.kuze.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "products")
public class Product {
    @Id
    private Long code;
    private String description;
    private String unitOfMeasure;
    private double price;


    public Product(Long code, String description, String unitOfMeasure, double price) {
        this.code = code;
        this.description = description;
        this.unitOfMeasure = unitOfMeasure;
        this.price = price;
    }

    public Product() {
    }
}
