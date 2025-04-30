package ch.example.springgraphqlkeycloakboilerplate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final RestTemplate restTemplate;
    private static final String API_URL = "https://api.weatherapi.com/v1/current.json?key=YOUR_API_KEY&q=";

    public String getWeatherForLocation(String location) {
        return "Sunny, 25°C in " + location;
    }
}