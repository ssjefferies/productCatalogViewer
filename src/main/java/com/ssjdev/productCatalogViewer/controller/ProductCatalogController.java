package com.ssjdev.productCatalogViewer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
	@PostMapping("/products")
	public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO newProduct) {
		ProductDTO savedProductDto = productService.addProduct(newProduct);
		return new ResponseEntity<>(savedProductDto, HttpStatus.CREATED);
	}
}