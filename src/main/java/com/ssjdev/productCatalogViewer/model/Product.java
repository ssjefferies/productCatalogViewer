package com.ssjdev.productCatalogViewer.model;

import java.math.BigDecimal;

import com.ssjdev.productCatalogViewer.dto.ProductDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Product")
public class Product {
	@Id
	@Column(name = "product_key")
	private Long id;
	
	@Column(name = "retailer")
	private String retailer;
	
	@Column(name = "brand")
	private String brand;
	
	@Column(name = "model")
	private String model;
	
	@Column(name = "product_name")
	private String productName;
	
	@Column(name = "product_description", columnDefinition = "TEXT")
	private String productDescription;
	
	@Column(name = "price")
	private BigDecimal price;
	
	public Product() {}
	
	public Product(ProductDTO productDTO) {
		this.setId(productDTO.getProductKey());
		this.setBrand(productDTO.getBrand());
		this.setRetailer(productDTO.getRetailer());
		this.setModel(productDTO.getModel());
		this.setProductName(productDTO.getProductName());
		this.setProductDescription(productDTO.getProductDescription());
		this.setPrice(productDTO.getPrice());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRetailer() {
		return retailer;
	}

	public void setRetailer(String retailer) {
		this.retailer = retailer;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
}