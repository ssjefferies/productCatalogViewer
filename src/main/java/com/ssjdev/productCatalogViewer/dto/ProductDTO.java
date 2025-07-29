package com.ssjdev.productCatalogViewer.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductDTO {
	@JsonProperty("product_key")
	private Long productKey;
	
	private String retailer;
	private String brand;
	private String model;
	
	@JsonProperty("product_name")
	private String productName;
	
	@JsonProperty("product_description")
	private String productDescription;
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