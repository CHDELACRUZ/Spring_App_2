package com.systemmanagmentstudent.springexercise2.service;

import com.systemmanagmentstudent.springexercise2.domain.Product;
import com.systemmanagmentstudent.springexercise2.dto.ProductDTO;
import com.systemmanagmentstudent.springexercise2.exceptions.CustomerNotFoundException;
import com.systemmanagmentstudent.springexercise2.exceptions.DuplicateNameException;
import com.systemmanagmentstudent.springexercise2.exceptions.ProductNotFoundException;
import com.systemmanagmentstudent.springexercise2.mapper.ProductMapper;
import com.systemmanagmentstudent.springexercise2.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
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
            throw new DuplicateNameException("Name already exists: " + productDTO.getName());
        }

        Product product = productMapper.toEntity(productDTO);
        Product productSaved = productRepository.save(product);

        return productMapper.toResponseDTO(productSaved);

    }

    public ProductDTO updateProduct(Integer id, ProductDTO productDTO) {

        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product id not found" + id));

        if(productDTO.getName() != null &&
            !existingProduct.getName().equals(productDTO.getName())) {
            throw new DuplicateNameException("Name already exists " + productDTO.getName());
        }
        productMapper.updateEntityFromDTO(productDTO, existingProduct);
        Product updatedProduct = productRepository.save(existingProduct);

        return productMapper.toResponseDTO(updatedProduct);
    }

    public ProductDTO partiallyUpdateProduct(Integer id, ProductDTO productDTO) {

        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product id not found" + id));

        if(productDTO.getName() != null &&
                !existingProduct.getName().equals(productDTO.getName())) {
            throw new DuplicateNameException("Name already exists " + productDTO.getName());
        }
        productMapper.updateEntityFromDTO(productDTO, existingProduct);
        Product updatedProduct = productRepository.save(existingProduct);

        return productMapper.toResponseDTO(updatedProduct);
    }

    public void deleteProduct(Integer id) {
        if(!productRepository.existsById(id)) {
            throw new CustomerNotFoundException("Product id not found" + id);
        }
        productRepository.deleteById(id);
    }


}
