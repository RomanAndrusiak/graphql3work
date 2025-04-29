package ch.example.springgraphqlkeycloakboilerplate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OwnerInput {
    private String name;
    private String email;
    private String phone;
}