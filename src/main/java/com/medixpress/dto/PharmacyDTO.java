package com.medixpress.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PharmacyDTO {
    private String id;
    private String name;
    private String email;
    private String password;
    private String contactNumber;
    private String address;
    private double latitude;
    private double longitude;
}
