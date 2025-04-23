package com.example.parcial2web.dtos.order;

import com.example.parcial2web.dtos.product.ResponseProductDTO;
import lombok.Data;

@Data
public class OrderItemDTO {
    private ResponseProductDTO product;
    private Integer quantity;
    private Double price;
}