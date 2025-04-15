package com.medixpress.dto;


import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {
    private String userId;
    private String pharmacyId;
    private List<OrderItemDTO> items;
}

