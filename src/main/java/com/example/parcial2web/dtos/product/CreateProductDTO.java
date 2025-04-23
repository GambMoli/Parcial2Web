package com.example.parcial2web.dtos.product;

import lombok.Data;

@Data
public class CreateProductDTO {
    private String name;
    private String description;
    private Double price;
    private Integer stock;
}