package com.example.parcial2web.services;

import com.example.parcial2web.dtos.order.CreateOrderDTO;
import com.example.parcial2web.dtos.order.OrderProductDTO;
import com.example.parcial2web.dtos.order.ResponseOrderDTO;
import com.example.parcial2web.dtos.order.UpdateOrderStatusDTO;
import com.example.parcial2web.exceptions.CustomException;
import com.example.parcial2web.mappers.OrderMapper;
import com.example.parcial2web.models.Customer;
import com.example.parcial2web.models.Order;
import com.example.parcial2web.models.OrderItem;
import com.example.parcial2web.models.Product;
import com.example.parcial2web.repositories.CustomerRepository;
import com.example.parcial2web.repositories.OrderRepository;
import com.example.parcial2web.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    private static final List<String> VALID_STATUSES = Arrays.asList("pendiente", "enviada", "entregada");

    @Transactional
    public ResponseOrderDTO createOrder(CreateOrderDTO dto) {
        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new CustomException("No existe un cliente con ese id", 404));

        Order order = orderMapper.toEntity(dto);
        order.setCustomer(customer);

        for (OrderProductDTO productDto : dto.getProducts()) {
            Product product = productRepository.findById(productDto.getProductId())
                    .orElseThrow(() -> new CustomException("No existe un producto con id: " + productDto.getProductId(), 404));

            if (product.getStock() < productDto.getQuantity()) {
                throw new CustomException("No hay stock para el producto: " + product.getName(), 400);
            }


            order.getProducts().add(product);

            OrderItem item = new OrderItem();
            item.setProductId(product.getId());
            item.setQuantity(productDto.getQuantity());
            item.setPrice(product.getPrice());
            order.getItems().add(item);

            product.setStock(product.getStock() - productDto.getQuantity());
            productRepository.save(product);
        }

        calculateOrderTotal(order);

        Order savedOrder = orderRepository.save(order);
        return orderMapper.toDto(savedOrder);
    }

    private void calculateOrderTotal(Order order) {
        double total = 0.0;
        for (OrderItem item : order.getItems()) {
            total += item.getPrice() * item.getQuantity();
        }
        order.setTotal(total);
    }

    public ResponseOrderDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new CustomException("No existe una orden con ese id", 404));
        return orderMapper.toDto(order);
    }

    public List<ResponseOrderDTO> getOrdersByCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomException("No existe un cliente con ese id", 404));

        List<Order> orders = orderRepository.findByCustomer(customer);
        return orders.stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ResponseOrderDTO updateOrderStatus(Long id, UpdateOrderStatusDTO dto) {
        if (!VALID_STATUSES.contains(dto.getStatus())) {
            throw new CustomException("Estado invalido. Estados permitidos: pendiente, enviada, entregada", 400);
        }

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new CustomException("No existe una orden con ese id", 404));

        order.setStatus(dto.getStatus());
        Order savedOrder = orderRepository.save(order);

        return orderMapper.toDto(savedOrder);
    }

    @Transactional
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new CustomException("No existe una orden con ese id", 404));

        for (OrderItem item : order.getItems()) {
            Product product = productRepository.findById(item.getProductId()).orElse(null);
            if (product != null) {
                product.setStock(product.getStock() + item.getQuantity());
                productRepository.save(product);
            }
        }

        orderRepository.deleteById(id);
    }

    public List<ResponseOrderDTO> getOrdersByStatus(String status) {
        if (!VALID_STATUSES.contains(status)) {
            throw new CustomException("Estado inválido. Estados permitidos: pendiente, enviada, entregada", 400);
        }

        List<Order> orders = orderRepository.findByStatus(status);
        return orders.stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ResponseOrderDTO> getRecentOrders(int days) {
        LocalDateTime date = LocalDateTime.now().minusDays(days);
        List<Order> orders = orderRepository.findOrdersAfterDate(date);
        return orders.stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

}