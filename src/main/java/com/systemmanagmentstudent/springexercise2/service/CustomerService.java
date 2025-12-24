package com.systemmanagmentstudent.springexercise2.service;

import com.systemmanagmentstudent.springexercise2.domain.Customer;
import com.systemmanagmentstudent.springexercise2.dto.CustomerDTO;
import com.systemmanagmentstudent.springexercise2.exceptions.CustomerNotFoundException;
import com.systemmanagmentstudent.springexercise2.exceptions.DuplicateUsernameException;
import com.systemmanagmentstudent.springexercise2.mapper.CustomerMapper;
import com.systemmanagmentstudent.springexercise2.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public List<CustomerDTO> getAllCustomers() {
        log.info("Fetching all customers");

        return customerRepository.findAll().stream()
                .map(customerMapper::toResponseDTO)
                .toList();
    }

    public CustomerDTO getCustomerById(Integer id) {
        log.info("Fetching customer with id: {}", id);
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer id not found: " + id));
        return customerMapper.toResponseDTO(customer);
    }

    public CustomerDTO getCustomerByUsername(String username) {
        log.info("Searching customer with username: {}", username);
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username is required");
        }

        Customer customer = customerRepository.findCustomerByUsernameIgnoreCase(username.trim())
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with username: " + username));

        return customerMapper.toResponseDTO(customer);
    }

    @Transactional
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        log.info("Creating customer with name: {}", customerDTO.getName());

        if(customerRepository.existsByName(customerDTO.getName())) {
            throw new DuplicateUsernameException("Customer name already exists: " + customerDTO);
        }

        Customer customer = customerMapper.toEntity(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);

        log.info("Customer created successfully with id: {}", savedCustomer.getId());
        return customerMapper.toResponseDTO(savedCustomer);
    }

    @Transactional
    public CustomerDTO updateCustomer(Integer id, CustomerDTO customerDTO) {
        log.info("Full update for customer with id: {}", id);

        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));

        //validateUsernameUnique(customerDTO.getUsername(), existingCustomer);

        if (customerDTO.getName() == null || customerDTO.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name is required for full update");
        }
        if (customerDTO.getUsername() == null || customerDTO.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username is required for full update");
        }
        if (customerDTO.getAge() == null) {
            throw new IllegalArgumentException("Age is required for full update");
        }
        if (customerDTO.getAddress() == null || customerDTO.getAddress().trim().isEmpty()) {
            throw new IllegalArgumentException("Address is required for full update");
        }

        existingCustomer.setName(customerDTO.getName().trim());
        existingCustomer.setUsername(customerDTO.getUsername().trim());
        existingCustomer.setAge(customerDTO.getAge());
        existingCustomer.setAddress(customerDTO.getAddress().trim());

        if (customerDTO.getPassword() != null && !customerDTO.getPassword().isEmpty()) {
            existingCustomer.setPassword(customerDTO.getPassword());
        }

        Customer updatedCustomer = customerRepository.save(existingCustomer);

        log.info("Customer fully updated successfully with id: {}", updatedCustomer.getId());
        return customerMapper.toResponseDTO(updatedCustomer);
    }

    @Transactional
    public CustomerDTO partialUpdateCustomer(Integer id, CustomerDTO customerDTO) {
        log.info("Partial update for customer with id: {}", id);

        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));

        if (customerDTO.getName() != null && !customerDTO.getName().trim().isEmpty()) {
            existingCustomer.setName(customerDTO.getName().trim());
        }

        if (customerDTO.getUsername() != null && !customerDTO.getUsername().trim().isEmpty()) {
            String newUsername = customerDTO.getUsername().trim();

            if (!existingCustomer.getUsername().equals(newUsername) &&
                    customerRepository.existsByUsername(newUsername)) {
                throw new DuplicateUsernameException("Username already exists: " + newUsername);
            }
            existingCustomer.setUsername(customerDTO.getUsername().trim());
        }

        if (customerDTO.getAge() != null) {
            existingCustomer.setAge(customerDTO.getAge());
        }

        if (customerDTO.getAddress() != null && !customerDTO.getAddress().trim().isEmpty()) {
            existingCustomer.setAddress(customerDTO.getAddress());
        }

        if (customerDTO.getPassword() != null && !customerDTO.getPassword().isEmpty()) {
            existingCustomer.setPassword(customerDTO.getPassword());
        }

        Customer updatedCustomer = customerRepository.save(existingCustomer);

        log.info("Customer partially updated successfully with id: {}", updatedCustomer.getId());
        return customerMapper.toResponseDTO(updatedCustomer);
    }

    /* private void validateUsernameUnique(String newUsername, Customer existingCustomer) {
        if (newUsername != null &&
                !existingCustomer.getUsername().equals(newUsername) &&
                customerRepository.existsByUsername(newUsername)) {
            throw new DuplicateUsernameException("Username already exists: " + newUsername);
        }
    } */

    @Transactional
    public void deleteCustomer(Integer id) {
        log.info("Deleting customer with id: {}", id);
        if (!customerRepository.existsById(id)) {
            throw new CustomerNotFoundException("Customer id not found: " + id);
        }

        customerRepository.deleteById(id);
        log.info("Customer deleted successfully with id: {}", id);
    }
}
