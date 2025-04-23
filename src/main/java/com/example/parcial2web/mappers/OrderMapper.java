package com.example.parcial2web.mappers;

import com.example.parcial2web.dtos.order.*;
import com.example.parcial2web.models.Order;
import com.example.parcial2web.models.OrderItem;
import com.example.parcial2web.models.Product;
import com.example.parcial2web.repositories.ProductRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "Spring", uses = {CustomerMapper.class, ProductMapper.class})
public abstract class OrderMapper {

    @Autowired
    protected CustomerMapper customerMapper;

    @Autowired
    protected ProductMapper productMapper;

    @Autowired
    protected ProductRepository productRepository;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "status", constant = "pendiente")
    @Mapping(target = "total", constant = "0.0")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "items", ignore = true)
    public abstract Order toEntity(CreateOrderDTO dto);

    @Mapping(target = "customer", expression = "java(customerMapper.toDto(order.getCustomer()))")
    @Mapping(target = "items", expression = "java(mapOrderItems(order))")
    public abstract ResponseOrderDTO toDto(Order order);

    protected List<OrderItemDTO> mapOrderItems(Order order) {
        List<OrderItemDTO> result = new ArrayList<>();

        for (OrderItem item : order.getItems()) {
            Product product = productRepository.findById(item.getProductId()).orElse(null);
            if (product != null) {
                OrderItemDTO dto = new OrderItemDTO();
                dto.setProduct(productMapper.toDto(product));
                dto.setQuantity(item.getQuantity());
                dto.setPrice(item.getPrice());
                result.add(dto);
            }
        }

        return result;
    }
}