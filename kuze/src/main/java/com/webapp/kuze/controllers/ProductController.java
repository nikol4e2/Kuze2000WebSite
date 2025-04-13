package com.webapp.kuze.controllers;

import com.webapp.kuze.model.Product;
import com.webapp.kuze.model.dtos.ProductDto;
import com.webapp.kuze.model.eceptions.ProductNotFoundException;
import com.webapp.kuze.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping
    public List<Product> findAll() {
        return productService.findAll();
    }

    @GetMapping("/{code}")
    public ResponseEntity<Product> findByCode(@PathVariable Long code) {
        Product product=this.productService.getProductByCode(code).orElseThrow(()->new ProductNotFoundException());

        return ResponseEntity.ok(product);

    }


    @DeleteMapping("/{code}")
    public ResponseEntity<Product> deleteByCode(@PathVariable Long code) {
        this.productService.deleteByCode(code);
        if (this.productService.getProductByCode(code).isEmpty()) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{code}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long code, @RequestBody ProductDto productDto) {
        if(this.productService.getProductByCode(code).isPresent()){
            Product product=this.productService.update(code,productDto);
            return ResponseEntity.ok(product);
        }
        return ResponseEntity.notFound().build();

    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductDto productDto) {
        if(this.productService.getProductByCode(productDto.getCode()).isPresent()){
            return ResponseEntity.badRequest().build();
        }
        Product product=this.productService.saveProduct(productDto);
        return ResponseEntity.ok(product);
    }
}
