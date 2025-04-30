package ch.example.springgraphqlkeycloakboilerplate.resolver;

import ch.example.springgraphqlkeycloakboilerplate.dto.UserDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserResolver {
    private final RestTemplate restTemplate;

    @QueryMapping
    public List<UserDto> users() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth("eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9VU0VSIiwiaWQiOjcsImVtYWlsIjoicm9ta28xNjRAZ21haWwuY29tIiwic3ViIjoicm9ta28xNjQiLCJpYXQiOjE3NDYwMDA5MzUsImV4cCI6MTc0NjYwNTczNX0.PpG86kh029_egXltsBWX8jpowPc5IwK6yubR-TMaV-8");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<UserDto[]> response = restTemplate.exchange(
                "http://localhost:8081/person",
                HttpMethod.GET,
                entity,
                UserDto[].class
        );
        UserDto[] usersArr = response.getBody();
        List<UserDto> users = new ArrayList<>();
        users.addAll(Arrays.asList(usersArr));
        return users;

    }

}