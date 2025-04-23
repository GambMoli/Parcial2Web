package com.example.parcial2web.mappers;


import com.example.parcial2web.dtos.client.CreateCustomerDTO;
import com.example.parcial2web.dtos.client.ResponseCustomerDTO;
import com.example.parcial2web.models.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface CustomerMapper {
    Customer toEntity(CreateCustomerDTO dto);

    ResponseCustomerDTO toDto(Customer client);

}
