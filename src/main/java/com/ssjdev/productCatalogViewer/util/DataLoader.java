package com.ssjdev.productCatalogViewer.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssjdev.productCatalogViewer.dto.ProductDTO;
import com.ssjdev.productCatalogViewer.model.Product;
import com.ssjdev.productCatalogViewer.respository.ProductRepository;

@Component
public class DataLoader implements CommandLineRunner {

    private final ProductRepository productRepository;

    public DataLoader(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    private List<Product> dtosToModels(List<ProductDTO> productDtoList) {
    	if  (productDtoList != null) {
    		return productDtoList.stream()
    				.map(currDto -> new Product(currDto))
    				.collect(Collectors.toList());
    	} else {
    		return new ArrayList<Product>();
    	}
    }

    @Override
    public void run(String... args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<ProductDTO>> typeReference = new TypeReference<List<ProductDTO>>() {};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/products.json");

        try {
            List<ProductDTO> productDTOs = mapper.readValue(inputStream, typeReference);
            // convert to data models
            productRepository.saveAll(dtosToModels(productDTOs));
            System.out.println("Products loaded from JSON and saved to database!");
        } catch (Exception e) {
            System.out.println("Unable to load products: " + e.getMessage());
        }
    }
}