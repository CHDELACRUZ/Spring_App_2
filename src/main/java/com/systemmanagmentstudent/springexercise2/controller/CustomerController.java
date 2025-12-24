package com.systemmanagmentstudent.springexercise2.controller;

import com.systemmanagmentstudent.springexercise2.dto.CustomerDTO;
import com.systemmanagmentstudent.springexercise2.service.CustomerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<CustomerDTO> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Integer id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity <CustomerDTO> getCustomerByUsername(@PathVariable String username) {
        return ResponseEntity.ok(customerService.getCustomerByUsername(username));
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        CustomerDTO customerCreated = customerService.createCustomer(customerDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(customerCreated);
    }

    @PutMapping("{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(
            @PathVariable Integer id,
            @Valid @RequestBody CustomerDTO customerDTO) {
        CustomerDTO customerUpdated = customerService.updateCustomer(id, customerDTO);

        return ResponseEntity.ok(customerUpdated);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CustomerDTO> partialUpdateCustomer(
            @PathVariable Integer id,
            @Valid @RequestBody CustomerDTO customerDTO) {
        CustomerDTO customerPartialUpdated = customerService.partialUpdateCustomer(id, customerDTO);

        return ResponseEntity.ok(customerPartialUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Integer id) {
        customerService.deleteCustomer(id);

        return ResponseEntity.noContent().build();
    }

}
