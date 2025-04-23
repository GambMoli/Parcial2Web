package com.example.parcial2web.dtos.order;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CreateOrderDTO {
    private Long customerId;
    private List<OrderProductDTO> products = new ArrayList<>();
}