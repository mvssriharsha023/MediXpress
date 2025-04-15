package com.medixpress.controller;

import com.medixpress.dto.OrderResponseDTO;
import com.medixpress.dto.OrderRequestDTO;
import com.medixpress.model.Order;
import com.medixpress.security.JwtUtil;
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
    @Autowired
    private JwtUtil jwtUtil;

    // Place a new order (from user's cart)
    @PostMapping("/place")
    public Order placeOrder(@RequestHeader String token) {
        String userId = jwtUtil.extractId(token);
        return orderService.placeOrder(userId);
    }

    // Get all orders by user
    @GetMapping("/user")
    public List<OrderResponseDTO> getOrdersByUser(@RequestHeader String token) {
        String userId = jwtUtil.extractId(token);
        return orderService.getOrdersByUser(userId);
    }

    // Get all orders by pharmacy
    @GetMapping("/pharmacy")
    public List<OrderResponseDTO> getOrdersByPharmacy(@RequestHeader String token) {
        String pharmacyId = jwtUtil.extractId(token);
        return orderService.getOrdersByPharmacy(pharmacyId);
    }

    // Get detailed order info
    @GetMapping("/{orderId}")
    public OrderResponseDTO getOrderDetails(@PathVariable String orderId) {
        return orderService.getOrderDetails(orderId);
    }
}
