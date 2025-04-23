package com.example.parcial2web.mappers;

import com.example.parcial2web.dtos.product.CreateProductDTO;
import com.example.parcial2web.dtos.product.ResponseProductDTO;
import com.example.parcial2web.models.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface ProductMapper {
    Product toEntity(CreateProductDTO dto);
    ResponseProductDTO toDto(Product product);
}