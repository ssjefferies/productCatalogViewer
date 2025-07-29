package com.ssjdev.productCatalogViewer.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssjdev.productCatalogViewer.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // You can add custom query methods here if needed
}