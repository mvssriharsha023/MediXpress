package com.medixpress.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicineDTO {

    private String id;
    private String name;
    private Double price;
    private Integer quantity;
    private String pharmacyId;

}

