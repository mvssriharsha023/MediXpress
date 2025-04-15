package com.medixpress.dto;


import com.medixpress.model.OrderStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {
    private String id;
    private String userId;
    private String pharmacyId;
    private Double totalAmount;
    private OrderStatus status;
    private LocalDateTime orderDateTime;
    private List<OrderItemDTO> items;
}

