package com.medixpress.model;



import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "order_items")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id
    private String id;

    private String medicineId;

    private Integer quantity;

    private Double price; // price per unit at time of order


    private Order order;
}

