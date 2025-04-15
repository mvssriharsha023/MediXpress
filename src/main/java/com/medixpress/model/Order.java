package com.medixpress.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    private String id;

    private String userId;

    private String pharmacyId;

    private Double totalAmount;

    private LocalDateTime orderDateTime;

    private String status;

    private List<OrderItem> items;
}

