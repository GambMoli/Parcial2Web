package com.example.parcial2web.services;

import com.example.parcial2web.dtos.client.CreateCustomerDTO;
import com.example.parcial2web.dtos.client.ResponseCustomerDTO;
import com.example.parcial2web.exceptions.CustomException;
import com.example.parcial2web.mappers.CustomerMapper;
import com.example.parcial2web.models.Customer;
import com.example.parcial2web.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private CustomerRepository customerRepository;

    public ResponseCustomerDTO createClient(CreateCustomerDTO dto) {
        Customer customer = customerMapper.toEntity(dto);
        if(customerRepository.existsByEmail(customer.getEmail())){
            throw new CustomException("Ya existe un cliente con el mismo email.", 404);
        }
        Customer customerSaved =  customerRepository.save(customer);

        return customerMapper.toDto(customerSaved);
    }

    public List<ResponseCustomerDTO> getAllCustomers () {
        return customerRepository.findAll().stream()
                .map(customerMapper::toDto)
                .collect(Collectors.toList());
    }

    public ResponseCustomerDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(
                () -> new CustomException("No existe un customer con ese id", 404)
        );

        return customerMapper.toDto(customer);
    }

    public ResponseCustomerDTO updateCustomer(Long id, CreateCustomerDTO dto) {
        Customer existingCustomer = customerRepository.findById(id).orElseThrow(
                () -> new CustomException("No existe un customer con ese id", 404)
        );

        if (!dto.getEmail().equals(existingCustomer.getEmail()) &&
                customerRepository.existsByEmail(dto.getEmail())) {
            throw new CustomException("Ya existe un customer con ese mail.", 400);
        }

        Customer updatedCustomerData = customerMapper.toEntity(dto);

        updatedCustomerData.setId(existingCustomer.getId());

        Customer savedCustomer = customerRepository.save(updatedCustomerData);

        return customerMapper.toDto(savedCustomer);
    }

    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new RuntimeException("No existe un customer con ese id");
        }

        customerRepository.deleteById(id);
    }
}