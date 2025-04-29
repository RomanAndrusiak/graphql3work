package ch.example.springgraphqlkeycloakboilerplate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OwnerDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private List<Long> carIds;
}