package com.medixpress.controller;

import com.medixpress.dto.OrderResponseDTO;
import com.medixpress.dto.OrderRequestDTO;
import com.medixpress.model.Order;
import com.medixpress.model.OrderStatus;
import com.medixpress.security.JwtUtil;
import com.medixpress.service.OrderService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Order> placeOrder(@RequestHeader String token) {
        String userId = jwtUtil.extractId(token);
        return ResponseEntity.ok(orderService.placeOrder(userId));
    }

    // Get all orders by user
    @GetMapping("/user")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByUser(@RequestHeader String token) {
        String userId = jwtUtil.extractId(token);
        return ResponseEntity.ok(orderService.getOrdersByUser(userId));
    }

    // Get all orders by pharmacy
    @GetMapping("/pharmacy")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByPharmacy(@RequestHeader String token) {
        String pharmacyId = jwtUtil.extractId(token);
        return ResponseEntity.ok(orderService.getOrdersByPharmacy(pharmacyId));
    }

    // Get detailed order info
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDTO> getOrderDetails(@PathVariable String orderId) {
        return ResponseEntity.ok(orderService.getOrderDetails(orderId));
    }

    // Update status to CANCELLED
    @PostMapping("/cancel/{orderId}")
    public ResponseEntity<Order> cancelOrder(@PathVariable String orderId, @RequestHeader String token) {
        String userId = jwtUtil.extractId(token);

        return ResponseEntity.ok(orderService.updateStatusByUser(userId, orderId, OrderStatus.CANCELLED));
    }

    @PostMapping("/delivered/{orderId}")
    public ResponseEntity<Order> deliveredOrder(@PathVariable String orderId, @RequestHeader String token) {
        String userId = jwtUtil.extractId(token);

        return ResponseEntity.ok(orderService.updateStatusByUser(userId, orderId, OrderStatus.DELIVERED));
    }

    @PostMapping("/outofdelivery/{orderId}")
    public ResponseEntity<Order> outOfDeliveryOrder(@PathVariable String orderId, @RequestHeader String token) {
        String pharmacyId = jwtUtil.extractId(token);

        return ResponseEntity.ok(orderService.updateStatusByUser(pharmacyId, orderId, OrderStatus.OUT_OF_DELIVERY));
    }
}
