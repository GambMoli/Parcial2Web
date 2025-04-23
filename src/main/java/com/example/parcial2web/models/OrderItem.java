package com.example.parcial2web.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long productId;
    private Integer quantity;
    private Double price;
}