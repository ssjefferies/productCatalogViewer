package com.ssjdev.productCatalogViewer.dto;

public class BrandSummaryDTO {
	private String brand;
	private Long count;
	
	public BrandSummaryDTO() {}
	
	public BrandSummaryDTO(String brand, Long count) {
		this.brand = brand;
		this.count = count;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}
}