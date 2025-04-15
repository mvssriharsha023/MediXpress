package com.medixpress.controller;

import com.medixpress.dto.OrderResponseDTO;
import com.medixpress.dto.OrderRequestDTO;
import com.medixpress.model.Order;
import com.medixpress.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin("*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Place a new order (from user's cart)
    @PostMapping("/place/{userId}")
    public Order placeOrder(@PathVariable String userId) {
        return orderService.placeOrder(userId);
    }

    // Get all orders by user
    @GetMapping("/user/{userId}")
    public List<OrderResponseDTO> getOrdersByUser(@PathVariable String userId) {
        return orderService.getOrdersByUser(userId);
    }

    // Get detailed order info
    @GetMapping("/{orderId}")
    public OrderResponseDTO getOrderDetails(@PathVariable String orderId) {
        return orderService.getOrderDetails(orderId);
    }
}
