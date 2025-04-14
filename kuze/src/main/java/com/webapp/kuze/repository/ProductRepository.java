package com.webapp.kuze.repository;

import com.webapp.kuze.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByDescriptionContainingIgnoreCase(String description);
    Page<Product> findAll(Pageable pageable);
}
