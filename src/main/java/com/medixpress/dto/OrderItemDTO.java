package com.medixpress.dto;



import com.medixpress.model.Order;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    private String id;
    private String medicineId;
    private Integer quantity;
    private Double pricePerUnit; // price per unit
    private Double totalPrice;
    private String orderId;
}

