package com.systemmanagmentstudent.springexercise2.service;

import com.systemmanagmentstudent.springexercise2.domain.Product;
import com.systemmanagmentstudent.springexercise2.dto.ProductDTO;
import com.systemmanagmentstudent.springexercise2.exceptions.DuplicateUsernameException;
import com.systemmanagmentstudent.springexercise2.exceptions.DuplicateProductException;
import com.systemmanagmentstudent.springexercise2.exceptions.ProductNotFoundException;
import com.systemmanagmentstudent.springexercise2.mapper.ProductMapper;
import com.systemmanagmentstudent.springexercise2.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toResponseDTO)
                .toList();
    }

    public ProductDTO getProductById(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product id not found" + id));

        return productMapper.toResponseDTO(product);
    }

    public List<ProductDTO> getProductByName(String name) {

        return productRepository.findByName(name).stream()
                .map(productMapper::toResponseDTO)
                .toList();
    }

    public ProductDTO createProduct(ProductDTO productDTO) {
        if(productRepository.existsByName(productDTO.getName())) {
            throw new DuplicateUsernameException("Name already exists: " + productDTO.getName());
        }

        Product product = productMapper.toEntity(productDTO);
        Product productSaved = productRepository.save(product);

        return productMapper.toResponseDTO(productSaved);

    }

    @Transactional
    public ProductDTO updateProduct(Integer id, ProductDTO productDTO) {
        log.info("Updating product with id: {}", id);
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Id not found" + id));

        if (productDTO.getName() != null &&
                !existingProduct.getName().equals(productDTO.getName())) {
            throw new DuplicateProductException("Product name already exists");
        }
        productMapper.updateEntityFromDTO(productDTO, existingProduct);
        Product updatedProduct = productRepository.save(existingProduct);

        log.info("product updated successfully with id: {}", updatedProduct.getId());
        return productMapper.toResponseDTO(updatedProduct);
    }

    @Transactional
    public ProductDTO partialUpdateProduct(Integer id, ProductDTO productDTO) {
        log.info("Partially updating product with id: {}", productDTO.getId());
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product id not found: " + id));

        if (productDTO.getName() != null &&
                !existingProduct.getName().equals(productDTO.getName())) {
            throw new DuplicateProductException("Product name already exists");
        }
        productMapper.updateEntityFromDTO(productDTO, existingProduct);
        Product partialUpdatedProduct = productRepository.save(existingProduct);

        log.info("Product partially updated successfully with id: {}", partialUpdatedProduct.getId());
        return productMapper.toResponseDTO(partialUpdatedProduct);
    }

    public void deleteProduct(Integer id) {
        if(!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product id not found" + id);
        }
        productRepository.deleteById(id);
    }
}
