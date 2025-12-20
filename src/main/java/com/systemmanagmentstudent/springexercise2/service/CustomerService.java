package com.systemmanagmentstudent.springexercise2.service;

import com.systemmanagmentstudent.springexercise2.domain.Customer;
import com.systemmanagmentstudent.springexercise2.dto.CustomerDTO;
import com.systemmanagmentstudent.springexercise2.exceptions.CustomerNotFoundException;
import com.systemmanagmentstudent.springexercise2.exceptions.DuplicateNameException;
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
                .orElseThrow(() -> new CustomerNotFoundException("Customer id not found" + id));
        return customerMapper.toResponseDTO(customer);
    }

    public List<CustomerDTO> getCustomerByName(String name) {
        log.info("Searching customer with name: {}", name);

        return customerRepository.findCustomerByName(name).stream()
                .map(customerMapper::toResponseDTO)
                .toList();
    }

    @Transactional
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        log.info("Creating customer with name: {}", customerDTO.getName());

        if(customerRepository.existsByName(customerDTO.getName())) {
            throw new DuplicateNameException("Customer name already exists" + customerDTO);
        }

        Customer customer = customerMapper.toEntity(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);

        log.info("Customer created successfully with id: {}", savedCustomer.getId());
        return customerMapper.toResponseDTO(savedCustomer);
    }

    @Transactional
    public CustomerDTO updateCustomer(Integer id, CustomerDTO customerDTO) {
        log.info("Updating customer with id: {}", + id);
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Id not found" + id));

        if (customerDTO.getName() != null &&
                !existingCustomer.getName().equals(customerDTO.getName())) {
            throw new DuplicateNameException("Name already exists");
        }
        customerMapper.updateEntityFromDTO(customerDTO, existingCustomer);
        Customer updatedCustomer = customerRepository.save(existingCustomer);

        log.info("Customer updated successfully with id: {}", updatedCustomer.getId());
        return customerMapper.toResponseDTO(updatedCustomer);
    }

    @Transactional
    public CustomerDTO partialUpdateCustomer(Integer id, CustomerDTO customerDTO) {
        log.info("Partially updating customer with id: {}", customerDTO.getId());
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer id not found: " + id));

        if (customerDTO.getName() != null &&
            !existingCustomer.getName().equals(customerDTO.getName())) {
            throw new DuplicateNameException("Name already exists");
        }
        customerMapper.updateEntityFromDTO(customerDTO, existingCustomer);
        Customer partialUpdatedCustomer = customerRepository.save(existingCustomer);

        log.info("Customer partially updated successfully with id: {}", partialUpdatedCustomer.getId());
        return customerMapper.toResponseDTO(partialUpdatedCustomer);
    }

    @Transactional
    public void deleteCustomer(Integer id) {
        log.info("Deleting customer with id: {}", id);
        if (!customerRepository.existsById(id)) {
            throw new CustomerNotFoundException("Customer id not found" + id);
        }

        customerRepository.deleteById(id);
        log.info("Customer deleted successfully with id: {}", id);
    }
}
