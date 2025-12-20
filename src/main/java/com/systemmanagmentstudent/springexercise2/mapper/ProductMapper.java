package com.systemmanagmentstudent.springexercise2.mapper;

import com.systemmanagmentstudent.springexercise2.domain.Product;
import com.systemmanagmentstudent.springexercise2.dto.ProductDTO;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductDTO toResponseDTO(Product product) {
        if (product == null) {
            return null;
        }

        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .build();
    }

    public Product toEntity(ProductDTO productDTO) {
        if (productDTO == null) {
            return null;
        }
        return Product.builder()
                .id(productDTO.getId())
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .stock(productDTO.getStock())
                .build();

    }

    public void updateEntityFromDTO(ProductDTO productDTO, Product product) {

        if (productDTO != null && product != null) {
            return;
        }

        if (productDTO.getName() != null && !productDTO.getName().isEmpty()) {
            product.setName(productDTO.getName());
        }

        if (productDTO.getPrice() != null) {
            product.setPrice(productDTO.getPrice());
        }

        if (productDTO.getStock() != null) {
            product.setStock(productDTO.getStock());
        }

    }


}
