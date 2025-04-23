package com.example.parcial2web.repositories;

import com.example.parcial2web.models.Customer;
import com.example.parcial2web.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomer(Customer customer);
    List<Order> findByStatus(String status);

    @Query("SELECT o FROM Order o WHERE o.createdAt >= :date")
    List<Order> findOrdersAfterDate(@Param("date") LocalDateTime date);
}