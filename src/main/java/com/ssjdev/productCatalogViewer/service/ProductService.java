package com.ssjdev.productCatalogViewer.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssjdev.productCatalogViewer.dto.BrandSummaryDTO;
import com.ssjdev.productCatalogViewer.dto.ProductDTO;
import com.ssjdev.productCatalogViewer.model.Product;
import com.ssjdev.productCatalogViewer.respository.ProductRepository;

/**
 * This service interacts with the product repository.  Data is mapped between the
 * database models and the Data Transfer Objects that are used in the REST services.
 */
@Service
public class ProductService {
	
	private ProductRepository productRepository;
	
	/**
	 * Constructor
	 * @param productRepository interacts with the product table in the database
	 */
	@Autowired
	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	/**
	 * Retrieves all products from the database.
	 * 
	 * This method fetches all product entities from the repository and converts them
	 * to Data Transfer Objects (DTOs) for client consumption.
	 * 
	 * @return a {@link List} of {@link ProductDTO} containing all products in the system.
	 *         Returns an empty list if no products are found.
	 * @throws DataAccessException if there is an error accessing the database
	 */
	public List<ProductDTO> getAllProducts() {
		List<ProductDTO> products = modelstoDto(productRepository.findAll());
		return products;
	}
	
	/**
	 * Finds a product by its unique identifier.
	 * 
	 * This method searches for a product in the database using the provided product ID
	 * and converts the found entity to a Data Transfer Object (DTO) for client use.
	 * 
	 * @param productId the unique identifier of the product to find. Must not be null.
	 * @return a {@link ProductDTO} containing the product data if found, 
	 *         or an empty {@link ProductDTO} with default values if not found
	 * @throws IllegalArgumentException if productId is null
	 * @throws DataAccessException if there is an error accessing the database
	 */
	public ProductDTO findProduct(Long productId) {
		Optional<Product> optionalEntity = productRepository.findById(productId);
	    if (optionalEntity.isPresent()) {
	    	return modelToDto(optionalEntity.get());
	    } else {
	    	return new ProductDTO();
	    }
	}
	
	/**
	 * Retrieves a summary of all brands in the product catalog, including the brand name
	 * and the number of products for each brand.
	 * 
	 * @return a {@link List} of {@link BrandSummaryDTO} containing summary information
	 *         for each brand. Returns an empty list if no brands are found.
	 * @throws DataAccessException if there is an error accessing the database
	 */
	public List<BrandSummaryDTO> getBrandSummary() {
		return productRepository.findBrandSummary();
	}
	
	/**
	 * Adds a new product to the database.
	 * 
	 * This method converts the provided ProductDTO to a Product entity,
	 * saves it to the database, and returns the saved product as a DTO.
	 * 
	 * @param newProduct the product data to be added. Must not be null.
	 * @return a {@link ProductDTO} representing the saved product
	 * @throws IllegalArgumentException if newProduct is null
	 * @throws DataIntegrityViolationException if the product data violates database constraints
	 * @throws DataAccessException if there is an error accessing the database
	 */
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