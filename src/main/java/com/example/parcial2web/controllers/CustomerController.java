package com.example.parcial2web.controllers;

import com.example.parcial2web.dtos.client.CreateCustomerDTO;
import com.example.parcial2web.dtos.client.ResponseCustomerDTO;
import com.example.parcial2web.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<ResponseCustomerDTO> createCustomer( @RequestBody CreateCustomerDTO dto) {
        ResponseCustomerDTO createdCustomer = customerService.createClient(dto);
        return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ResponseCustomerDTO>> getAllCustomers() {
        List<ResponseCustomerDTO> customers = customerService.getAllCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseCustomerDTO> getCustomerById(@PathVariable Long id) {
        ResponseCustomerDTO customer = customerService.getCustomerById(id);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseCustomerDTO> updateCustomer(
            @PathVariable Long id,
            @RequestBody CreateCustomerDTO dto) {
        ResponseCustomerDTO updatedCustomer = customerService.updateCustomer(id, dto);
        return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}