package com.medixpress.service;



import com.medixpress.dto.OrderResponseDTO;
import com.medixpress.model.Order;
import com.medixpress.model.OrderStatus;

import java.util.List;

public interface OrderService {

    Order placeOrder(String userId);

    List<OrderResponseDTO> getOrdersByUser(String userId);

    List<OrderResponseDTO> getOrdersByPharmacy(String pharmacyId);

    OrderResponseDTO getOrderDetails(String orderId);

    Order updateStatusByUser(String userId, String orderId, OrderStatus status);

    Order updateStatusByPharmacy(String pharmacyId, String orderId, OrderStatus status);
}

