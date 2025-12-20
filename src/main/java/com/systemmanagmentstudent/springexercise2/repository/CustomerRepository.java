package com.systemmanagmentstudent.springexercise2.repository;

import com.systemmanagmentstudent.springexercise2.domain.Customer;
import com.systemmanagmentstudent.springexercise2.dto.CustomerDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository <Customer, Integer> {

    Optional<Customer> findCustomerByName(String name);

    boolean existsByName(String name);

}
