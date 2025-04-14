package com.webapp.kuze.service.impl;

import com.webapp.kuze.model.Product;
import com.webapp.kuze.model.dtos.ProductDto;
import com.webapp.kuze.model.eceptions.ProductNotFoundException;
import com.webapp.kuze.repository.ProductRepository;
import com.webapp.kuze.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl  implements ProductService {


    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product save(Long code, String description, String unitOfMeasure, double price) {
        if(code == null || description == null || unitOfMeasure == null || price < 0) throw new IllegalArgumentException("All parameters are required");

        return productRepository.save(new Product(code,description,unitOfMeasure,price));
    }

    @Override
    public Optional<Product> getProductByCode(Long code) {
        return productRepository.findById(code);
    }

    @Override
    public List<Product> findAll() {

        return productRepository.findAll();
    }

    @Override
    public List<Product> findAllByDescriptionContainingIgnoreCase(String description) {
        return productRepository.findAllByDescriptionContainingIgnoreCase(description);
    }

    @Override
    public Boolean deleteByCode(Long code) {
        if(this.productRepository.existsById(code)) {
            this.productRepository.deleteById(code);
            return true;
        }
        return false;
    }

    @Override
    public Optional<Product> editProduct(Long code, String description, String unitOfMeasure, double price) {
        Product product = getProductByCode(code).orElseThrow(()->new ProductNotFoundException());
        product.setDescription(description);
        product.setUnitOfMeasure(unitOfMeasure);
        product.setPrice(price);
        return Optional.of(productRepository.save(product));
    }

    @Override
    public Product saveProduct(ProductDto productDto) {
        Product product=new Product(productDto.getCode(),productDto.getDescription(),productDto.getUnitOfMeasure(),productDto.getPrice());
        return productRepository.save(product);
    }

    @Override
    public Product update(Long productCode, ProductDto productDto) {
        Product existingProduct=productRepository.findById(productCode).orElseThrow(()->new ProductNotFoundException());
        existingProduct.setDescription(productDto.getDescription());
        existingProduct.setUnitOfMeasure(productDto.getUnitOfMeasure());
        existingProduct.setPrice(productDto.getPrice());
        return productRepository.save(existingProduct);
    }

    @Override
    public Page<Product> getAllPaged(Pageable pageable) {
        return productRepository.findAll(pageable);
    }
}
