package com.systemmanagmentstudent.springexercise2.repository;

import com.systemmanagmentstudent.springexercise2.domain.Customer;
import com.systemmanagmentstudent.springexercise2.domain.Product;

import com.systemmanagmentstudent.springexercise2.dto.ProductDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Optional<Product> findByName(String name);

    boolean existsByName(String name);

    boolean existsById(Integer id);
}
