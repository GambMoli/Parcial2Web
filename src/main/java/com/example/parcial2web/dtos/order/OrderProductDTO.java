package com.example.parcial2web.dtos.order;

import lombok.Data;

@Data
public class OrderProductDTO {
    private Long productId;
    private Integer quantity;
}