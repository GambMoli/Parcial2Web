package com.example.parcial2web.dtos.order;

import com.example.parcial2web.dtos.client.ResponseCustomerDTO;
import com.example.parcial2web.dtos.product.ResponseProductDTO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ResponseOrderDTO {
    private Long id;
    private ResponseCustomerDTO customer;
    private String status;
    private Double total;
    private LocalDateTime createdAt;
    private List<OrderItemDTO> items = new ArrayList<>();
}