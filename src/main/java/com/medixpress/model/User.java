package com.medixpress.model;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    private String id;

    private String name;

    @NotNull
    @Indexed(unique = true)
    private String email;

    private String password;

    @NotNull
    @Indexed(unique = true)
    private String contactNumber;

    private String address;

    private double latitude;
    private double longitude;
}
