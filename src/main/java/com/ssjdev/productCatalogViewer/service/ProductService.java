package com.ssjdev.productCatalogViewer.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssjdev.productCatalogViewer.dto.BrandSummaryDTO;
import com.ssjdev.productCatalogViewer.dto.ProductDTO;
import com.ssjdev.productCatalogViewer.model.Product;
import com.ssjdev.productCatalogViewer.respository.ProductRepository;

@Service
public class ProductService {
	
	private ProductRepository productRepository;
	
	@Autowired
	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	public List<ProductDTO> getAllProducts() {
		List<ProductDTO> products = modelstoDto(productRepository.findAll());
		return products;
	}
	
	public ProductDTO findProduct(Long productId) {
		return modelToDto(productRepository.getById(productId));
	}
	
	public List<BrandSummaryDTO> getBrandSummary() {
		return productRepository.findBrandSummary();
	}
	
	public ProductDTO addProduct(ProductDTO newProduct) {
		return modelToDto(productRepository.save(new Product(newProduct)));
	}

	private List<ProductDTO> modelstoDto(List<Product> models) {
		if (models != null) {
			return models.stream()
					.map(currModel -> modelToDto(currModel))
					.collect(Collectors.toList());
		} else {
			return new ArrayList<ProductDTO>();
		}
	}
	
	private ProductDTO modelToDto(Product product) {
		return new ProductDTO()
				.setProductKey(product.getId())
				.setRetailer(product.getRetailer())
				.setBrand(product.getBrand())
				.setModel(product.getModel())
				.setProductName(product.getProductName())
				.setProductDescription(product.getProductDescription())
				.setPrice(product.getPrice());
	}
}