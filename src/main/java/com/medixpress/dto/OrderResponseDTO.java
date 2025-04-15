package com.medixpress.dto;


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
    private String status;
    private LocalDateTime orderDateTime;
    private List<OrderItemDTO> items;
}

