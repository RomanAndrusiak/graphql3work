package ch.example.springgraphqlkeycloakboilerplate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDTO {
    private Long id;
    private String model;
    private Integer year;
    private String color;
    private Long ownerId;
}