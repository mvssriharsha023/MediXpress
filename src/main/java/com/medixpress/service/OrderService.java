package com.medixpress.service;



import com.medixpress.dto.OrderResponseDTO;
import com.medixpress.model.Order;

import java.util.List;

public interface OrderService {

    Order placeOrder(String userId);

    List<OrderResponseDTO> getOrdersByUser(String userId);

    OrderResponseDTO getOrderDetails(String orderId);
}

