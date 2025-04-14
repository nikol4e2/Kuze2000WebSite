package com.webapp.kuze.service;

import com.webapp.kuze.model.Product;
import com.webapp.kuze.model.dtos.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Product saveProduct(ProductDto productDto);
    Product save(Long code, String description,  String unitOfMeasure,double price);

    List<Product> findAll();
    Optional<Product> getProductByCode(Long code);
    List<Product> findAllByDescriptionContainingIgnoreCase(String description);
    Boolean deleteByCode(Long code);
    Optional<Product> editProduct(Long code, String description, String unitOfMeasure, double price);
    Product update(Long code, ProductDto productDto);
    Page<Product> getAllPaged(Pageable pageable);
}
