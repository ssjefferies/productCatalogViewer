package com.ssjdev.productCatalogViewer.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ssjdev.productCatalogViewer.dto.BrandSummaryDTO;
import com.ssjdev.productCatalogViewer.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
	@Query("SELECT new com.ssjdev.productCatalogViewer.dto.BrandSummaryDTO(brand, COUNT(brand)) as productCount FROM Product GROUP BY brand")
	List<BrandSummaryDTO> findBrandSummary();
}