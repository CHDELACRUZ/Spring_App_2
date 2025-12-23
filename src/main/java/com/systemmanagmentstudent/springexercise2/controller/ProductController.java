package com.systemmanagmentstudent.springexercise2.controller;

import com.systemmanagmentstudent.springexercise2.dto.ProductDTO;
import com.systemmanagmentstudent.springexercise2.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Integer id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<ProductDTO>> getProductByName(@PathVariable String name) {
        return ResponseEntity.ok(productService.getProductByName(name));
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        ProductDTO productCreated = productService.createProduct(productDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(productCreated);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable Integer id,
            @Valid @RequestBody ProductDTO productDTO) {
        ProductDTO productUpdated = productService.updateProduct(id, productDTO);
        return ResponseEntity.ok(productUpdated);
    }

    @PatchMapping("/partialUpdate/{id}")
    public ResponseEntity<ProductDTO> partialUpdateProduct(
            @PathVariable Integer id,
            @Valid @RequestBody ProductDTO productDTO) {
        ProductDTO productPartialUpdated = productService.partialUpdateProduct(id, productDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(productPartialUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
