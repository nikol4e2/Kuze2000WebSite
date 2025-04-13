package com.webapp.kuze.model.dtos;

import lombok.Data;

@Data
public class ProductDto {
    private Long code;
    private String description;
    private String unitOfMeasure;
    private double price;


    public ProductDto(Long code, String description, double price, String unitOfMeasure) {
        this.code = code;
        this.description = description;
        this.price = price;
        this.unitOfMeasure = unitOfMeasure;
    }
}
