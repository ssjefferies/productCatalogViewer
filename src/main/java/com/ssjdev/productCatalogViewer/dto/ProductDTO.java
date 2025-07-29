package com.ssjdev.productCatalogViewer.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ProductDTO {
	@NotNull(message = "Product key cannot be blank")
	@JsonProperty("product_key")
	private Long productKey;
	
	@Size(max = 100, message = "Retailer can be up to 100 characters at most")
	private String retailer;
	@Size(max = 100, message = "Brand can be up to 100 characters at most")
	private String brand;
	@Size(max = 50, message = "Model can be up to 50 characters at most")
	private String model;
	
	@Size(max = 500, message = "Product name can be up to 100 characters at most")
	@JsonProperty("product_name")
	private String productName;
	
	@Size(max = 10000, message = "Product Description can be up to 10,000 characters at most")
	@JsonProperty("product_description")
	private String productDescription;
	
	// Price can be null, but if present, must be at least 0.01 and have at most 7 integer digits and 4 fraction digits.
    @DecimalMin(value = "0.01", message = "Price must be at least 0.01")
    @Digits(integer = 7, fraction = 4, message = "Price must have at most 7 integer digits and 4 fraction digits")
	private BigDecimal price;
	
	public ProductDTO() {}

	public Long getProductKey() {
		return productKey;
	}

	public ProductDTO setProductKey(Long productKey) {
		this.productKey = productKey;
		return this;
	}

	public String getRetailer() {
		return retailer;
	}

	public ProductDTO setRetailer(String retailer) {
		this.retailer = retailer;
		return this;
	}

	public String getBrand() {
		return brand;
	}

	public ProductDTO setBrand(String brand) {
		this.brand = brand;
		return this;
	}

	public String getModel() {
		return model;
	}

	public ProductDTO setModel(String model) {
		this.model = model;
		return this;
	}

	public String getProductName() {
		return productName;
	}

	public ProductDTO setProductName(String productName) {
		this.productName = productName;
		return this;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public ProductDTO setProductDescription(String productDescription) {
		this.productDescription = productDescription;
		return this;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public ProductDTO setPrice(BigDecimal price) {
		this.price = price;
		return this;
	}
}