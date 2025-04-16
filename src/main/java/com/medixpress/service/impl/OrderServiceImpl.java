package com.medixpress.service.impl;

import com.medixpress.dto.OrderResponseDTO;
import com.medixpress.dto.OrderItemDTO;
import com.medixpress.exception.CartEmptyException;
import com.medixpress.exception.MedicineNotFoundException;
import com.medixpress.exception.OutOfStockException;
import com.medixpress.model.*;
import com.medixpress.repository.CartRepository;
import com.medixpress.repository.MedicineRepository;
import com.medixpress.repository.OrderItemRepository;
import com.medixpress.repository.OrderRepository;
import com.medixpress.service.CartService;
import com.medixpress.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private CartService cartService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private MedicineRepository medicineRepository;

    @Override
    @Transactional
    public Order placeOrder(String userId) {
        List<CartItem> cartItems = cartRepository.findByUserId(userId);
        if (cartItems.isEmpty()) {
            throw new CartEmptyException("Cart is empty");
        }

        // Create order
        Order order = Order.builder()
                .userId(userId)
                .pharmacyId(cartItems.getFirst().getPharmacyId()) // Assuming all items are from one pharmacy
                .orderDateTime(LocalDateTime.now())
                .totalAmount(0.0) // will update later
                .build();

        order = orderRepository.save(order);

        double total = 0.0;

        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem item : cartItems) {
            Medicine med = medicineRepository.findById(item.getMedicineId())
                    .orElseThrow(() -> new MedicineNotFoundException("Medicine not found"));

            if (med.getQuantity() < item.getQuantity()) {
                throw new OutOfStockException("Medicine " + med.getName() + " is out of stock.");
            }

            double price = med.getPrice() * item.getQuantity();

            OrderItem orderItem = OrderItem.builder()
                    .orderId(order.getId())
                    .medicineId(item.getMedicineId())
                    .pricePerUnit(med.getPrice())
                    .quantity(item.getQuantity())
                    .totalPrice(price)
                    .build();

            orderItemRepository.save(orderItem);
            orderItems.add(orderItem);

            // Update stock
            med.setQuantity(med.getQuantity() - item.getQuantity());
            medicineRepository.save(med);

            total += price;
        }

        order.setItems(orderItems);
        order.setTotalAmount(total);
        order.setStatus(OrderStatus.PLACED);
        orderRepository.save(order);

        // Clear the cart
        cartService.clearCart(userId);
        return order;
    }

    @Override
    public List<OrderResponseDTO> getOrdersByUser(String userId) {
//        return orderRepository.findByUserId(userId).stream()
//                .map(order -> OrderResponseDTO.builder()
//                        .id(order.getId())
//                        .userId(order.getUserId())
//                        .pharmacyId(order.getPharmacyId())
//                        .orderDateTime(order.getOrderDateTime())
//                        .totalAmount(order.getTotalAmount())
//                        .build())
//                .collect(Collectors.toList());
        return orderRepository.findByUserId(userId).stream()
                .map(order -> {
                    List<OrderItemDTO> items = orderItemRepository.findByOrderId(order.getId())
                            .stream()
                            .map(item -> OrderItemDTO.builder()
                                    .medicineId(item.getMedicineId())
                                    .quantity(item.getQuantity())
                                    .pricePerUnit(item.getPricePerUnit())
                                    .totalPrice(item.getTotalPrice())
                                    .build())
                            .collect(Collectors.toList());

                    return OrderResponseDTO.builder()
                            .id(order.getId())
                            .userId(order.getUserId())
                            .pharmacyId(order.getPharmacyId())
                            .orderDateTime(order.getOrderDateTime())
                            .totalAmount(order.getTotalAmount())
                            .items(items)
                            .build();
                })
                .collect(Collectors.toList());

    }

    @Override
    public List<OrderResponseDTO> getOrdersByPharmacy(String pharmacyId) {
//        return orderRepository.findByPharmacyId(pharmacyId).stream()
//                .map(order -> OrderResponseDTO.builder()
//                        .id(order.getId())
//                        .userId(order.getUserId())
//                        .pharmacyId(order.getPharmacyId())
//                        .orderDateTime(order.getOrderDateTime())
//                        .totalAmount(order.getTotalAmount())
//                        .build())
//                .collect(Collectors.toList());
        return orderRepository.findByPharmacyId(pharmacyId).stream()
                .map(order -> {
                    List<OrderItemDTO> items = orderItemRepository.findByOrderId(order.getId())
                            .stream()
                            .map(item -> OrderItemDTO.builder()
                                    .medicineId(item.getMedicineId())
                                    .quantity(item.getQuantity())
                                    .pricePerUnit(item.getPricePerUnit())
                                    .totalPrice(item.getTotalPrice())
                                    .build())
                            .collect(Collectors.toList());

                    return OrderResponseDTO.builder()
                            .id(order.getId())
                            .userId(order.getUserId())
                            .pharmacyId(order.getPharmacyId())
                            .orderDateTime(order.getOrderDateTime())
                            .totalAmount(order.getTotalAmount())
                            .items(items)
                            .build();
                })
                .collect(Collectors.toList());

    }

    @Override
    public OrderResponseDTO getOrderDetails(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        List<OrderItemDTO> items = orderItemRepository.findByOrderId(orderId)
                .stream()
                .map(item -> OrderItemDTO.builder()
                        .medicineId(item.getMedicineId())
                        .pricePerUnit(item.getPricePerUnit())
                        .quantity(item.getQuantity())
                        .totalPrice(item.getTotalPrice())
                        .build())
                .collect(Collectors.toList());

        return OrderResponseDTO.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .pharmacyId(order.getPharmacyId())
                .orderDateTime(order.getOrderDateTime())
                .totalAmount(order.getTotalAmount())
                .items(items)
                .build();
    }
}
