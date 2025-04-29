package ch.example.springgraphqlkeycloakboilerplate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    private final RestTemplate restTemplate;
    private static final String API_URL = "https://api.weatherapi.com/v1/current.json?key=YOUR_API_KEY&q=";

    @Autowired
    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getWeatherForLocation(String location) {
        // In a real application, you would use the actual API
        // For demo purposes, we'll return mock data
        return "Sunny, 25°C in " + location;
    }
}