package com.ssjdev.productCatalogViewer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssjdev.productCatalogViewer.dto.BrandSummaryDTO;
import com.ssjdev.productCatalogViewer.dto.ProductDTO;
import com.ssjdev.productCatalogViewer.service.ProductService;

@RestController
@RequestMapping("/api")
public class ProductCatalogController {
	
	private ProductService productService;

	@Autowired
	public ProductCatalogController(ProductService productService) {
		this.productService = productService;
	}

	
	@GetMapping("/products")
	public List<ProductDTO> getAllProducts() {
		return productService.getAllProducts();
	}
	
	@GetMapping("/products/{productKey}")
	public ResponseEntity<ProductDTO> getProductById(@PathVariable Long productKey) {
		ProductDTO product = productService.findProduct(productKey);
	    
	    // Check if product exists (assuming empty ProductDTO has null ID or use your own check)
	    if (product.getProductKey() == null) {
	        return ResponseEntity.notFound().build();
	    }
	    
	    return ResponseEntity.ok(product);
	}
	
	@GetMapping("/products/brand-summary")
	public List<BrandSummaryDTO> getBrandSummary() {
		return productService.getBrandSummary();
	}
	
	@PostMapping("/products")
	public ResponseEntity<?> addProduct(@Validated @RequestBody ProductDTO newProduct) {
		try {
			ProductDTO savedProductDto = productService.addProduct(newProduct);
			return new ResponseEntity<>(savedProductDto, HttpStatus.CREATED);
		} catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating product: " + e.getMessage());
        }
	}
}