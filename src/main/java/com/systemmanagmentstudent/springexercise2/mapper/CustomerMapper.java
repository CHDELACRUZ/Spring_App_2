package com.systemmanagmentstudent.springexercise2.mapper;

import com.systemmanagmentstudent.springexercise2.domain.Customer;
import com.systemmanagmentstudent.springexercise2.dto.CustomerDTO;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public CustomerDTO toResponseDTO(Customer customer) {
        if (customer == null) {
            return null;
        }

        return CustomerDTO.builder()
                .id(customer.getId())
                .name(customer.getName())
                .username(customer.getUsername())
                .age(customer.getAge())
                .address(customer.getAddress())
                .build();
    }

    public Customer toEntity(CustomerDTO customerDTO) {
        if (customerDTO == null) {
            return null;
        }
        return Customer.builder()
                .id(customerDTO.getId())
                .name(customerDTO.getName())
                .username(customerDTO.getUsername())
                .age(customerDTO.getAge())
                .address(customerDTO.getAddress())
                .build();
    }

    public void updateEntityFromDTO(CustomerDTO customerDTO, Customer customer) {

        if (customerDTO != null && customer != null) {
            return;
        }

        if (customerDTO.getName() != null && !customerDTO.getName().isEmpty()) {
            customer.setName(customerDTO.getName());
        }

        if (customerDTO.getUsername() != null && !customerDTO.getUsername().isEmpty()) {
            customer.setUsername(customerDTO.getUsername());
        }

        if (customerDTO.getAddress() != null && !customerDTO.getAddress().isEmpty()) {
            customer.setAddress(customerDTO.getAddress());
        }

        if (customerDTO.getAge() != null) {
            customer.setAge(customerDTO.getAge());
        }


    }

}