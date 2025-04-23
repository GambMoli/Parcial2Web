package com.example.parcial2web.controllers;

import com.example.parcial2web.dtos.order.CreateOrderDTO;
import com.example.parcial2web.dtos.order.ResponseOrderDTO;
import com.example.parcial2web.dtos.order.UpdateOrderStatusDTO;
import com.example.parcial2web.dtos.product.ResponseProductDTO;
import com.example.parcial2web.services.OrderService;
import com.example.parcial2web.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<ResponseOrderDTO> createOrder(@RequestBody CreateOrderDTO dto) {
        ResponseOrderDTO createdOrder = orderService.createOrder(dto);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseOrderDTO> getOrderById(@PathVariable Long id) {
        ResponseOrderDTO order = orderService.getOrderById(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<ResponseOrderDTO>> getOrdersByCustomer(@PathVariable Long customerId) {
        List<ResponseOrderDTO> orders = orderService.getOrdersByCustomer(customerId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ResponseOrderDTO> updateOrderStatus(
            @PathVariable Long id,
            @RequestBody UpdateOrderStatusDTO dto) {
        ResponseOrderDTO updatedOrder = orderService.updateOrderStatus(id, dto);
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ResponseOrderDTO>> getOrdersByStatus(@PathVariable String status) {
        List<ResponseOrderDTO> orders = orderService.getOrdersByStatus(status);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/recent/{days}")
    public ResponseEntity<List<ResponseOrderDTO>> getRecentOrders(@PathVariable int days) {
        List<ResponseOrderDTO> orders = orderService.getRecentOrders(days);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

}