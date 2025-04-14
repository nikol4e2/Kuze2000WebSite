package com.webapp.kuze.controllers;

import com.webapp.kuze.model.Product;
import com.webapp.kuze.model.dtos.ProductDto;
import com.webapp.kuze.model.eceptions.ProductNotFoundException;
import com.webapp.kuze.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping
    public List<Product> findAll() {
        return productService.findAll();
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

    @GetMapping("/pagination")
    public Page<Product> findAllWithPagination(Pageable pageable) {
        return this.productService.getAllPaged(pageable);
    }

    @GetMapping("/search")
    public List<Product> search(@RequestParam String keyword) {
        return productService.findAllByDescriptionContainingIgnoreCase(keyword);
    }


    @GetMapping("/{code}")
    public ResponseEntity<Product> findByCode(@PathVariable Long code) {
        Product product=this.productService.getProductByCode(code).orElseThrow(()->new ProductNotFoundException());

        return ResponseEntity.ok(product);

    }
}
