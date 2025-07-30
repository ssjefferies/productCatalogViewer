package com.ssjdev.productCatalogViewer.service;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ssjdev.productCatalogViewer.dto.ProductDTO;
import com.ssjdev.productCatalogViewer.model.Product;
import com.ssjdev.productCatalogViewer.respository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
	
	public static final Long ID_1 = 123456L;
	public static final String BRAND_1 = "brand 1";
	public static final String RETAILER_1 = "retailer 1";
	public static final String MODEL_1 = "model 1";
	public static final String PRODUCT_NAME_1 = "product name 1";
	public static final String DESCRIPTION_1 = "description 1";
	public static final BigDecimal PRICE_1 = new BigDecimal("4.55");
	
	private static final ProductDTO PRODUCT_DTO_1 = new ProductDTO()
			.setProductKey(ID_1)
			.setBrand(BRAND_1)
			.setRetailer(RETAILER_1)
			.setModel(MODEL_1)
			.setProductName(PRODUCT_NAME_1)
			.setProductDescription(DESCRIPTION_1)
			.setPrice(PRICE_1);
	
	private static final Product PRODUCT_MODEL_1 = new Product(PRODUCT_DTO_1);
	
	@Mock ProductRepository productRepository; // Mock a dependency
	
	@InjectMocks
	private ProductService productService; // Inject mocks into the service being tested
	
	@Test
	void testFindProduct() {
		Mockito.when(productRepository.findById(ID_1)).thenReturn(java.util.Optional.of(PRODUCT_MODEL_1));
		ProductDTO productDto = productService.findProduct(ID_1);
		
		// Make sure the product DTO returned by the service contains the values we expect.
		// They should match the values in the database model.
		Assertions.assertEquals(ID_1, productDto.getProductKey());
		Assertions.assertEquals(BRAND_1, productDto.getBrand());
		Assertions.assertEquals(RETAILER_1, productDto.getRetailer());
		Assertions.assertEquals(MODEL_1, productDto.getModel());
		Assertions.assertEquals(PRODUCT_NAME_1, productDto.getProductName());
		Assertions.assertEquals(DESCRIPTION_1, productDto.getProductDescription());
		Assertions.assertEquals(PRICE_1, productDto.getPrice());

		Mockito.verify(productRepository).findById(ID_1); // Verify interaction with mock
	}
}