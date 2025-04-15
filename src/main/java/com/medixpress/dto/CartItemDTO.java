package com.medixpress.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDTO {
    private String id;
    private String userId;
    private String pharmacyId;
    private String medicineId;
    private Integer quantity;
    private LocalDateTime addedAt;
}
