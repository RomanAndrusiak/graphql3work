package ch.example.springgraphqlkeycloakboilerplate.resolver;

import ch.example.springgraphqlkeycloakboilerplate.dto.CarDTO;
import ch.example.springgraphqlkeycloakboilerplate.dto.OwnerDTO;
import ch.example.springgraphqlkeycloakboilerplate.dto.OwnerInput;
import ch.example.springgraphqlkeycloakboilerplate.entity.Car;
import ch.example.springgraphqlkeycloakboilerplate.entity.Owner;
import ch.example.springgraphqlkeycloakboilerplate.exception.GraphQLException;
import ch.example.springgraphqlkeycloakboilerplate.repository.OwnerRepository;
import ch.example.springgraphqlkeycloakboilerplate.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class OwnerResolver {

    private final OwnerRepository ownerRepository;
    private final WeatherService weatherService;

    @Autowired
    public OwnerResolver(OwnerRepository ownerRepository, WeatherService weatherService) {
        this.ownerRepository = ownerRepository;
        this.weatherService = weatherService;
    }

    @QueryMapping
    public List<OwnerDTO> owners() {
        return ownerRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @QueryMapping
    public OwnerDTO ownerById(@Argument Long id) {
        return ownerRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new GraphQLException("Owner not found with id: " + id));
    }

    @QueryMapping
    public String weatherByLocation(@Argument String location) {
        return weatherService.getWeatherForLocation(location);
    }

    @MutationMapping
    public OwnerDTO createOwner(@Argument OwnerInput input) {
        Owner owner = new Owner();
        owner.setName(input.getName());
        owner.setEmail(input.getEmail());
        owner.setPhone(input.getPhone());

        return mapToDTO(ownerRepository.save(owner));
    }

    @MutationMapping
    public OwnerDTO updateOwner(@Argument Long id, @Argument OwnerInput input) {
        Owner owner = ownerRepository.findById(id)
                .orElseThrow(() -> new GraphQLException("Owner not found with id: " + id));

        owner.setName(input.getName());
        owner.setEmail(input.getEmail());
        owner.setPhone(input.getPhone());

        return mapToDTO(ownerRepository.save(owner));
    }

    @MutationMapping
    public Boolean deleteOwner(@Argument Long id) {
        if (!ownerRepository.existsById(id)) {
            throw new GraphQLException("Owner not found with id: " + id);
        }
        ownerRepository.deleteById(id);
        return true;
    }
    @SchemaMapping(typeName = "Owner", field = "cars")
    public List<CarDTO> getCars(OwnerDTO owner) {
        Owner ownerEntity = ownerRepository.findById(owner.getId())
                .orElseThrow(() -> new GraphQLException("Owner not found with id: " + owner.getId()));

        return ownerEntity.getCars().stream()
                .map(car -> new CarDTO(
                        car.getId(),
                        car.getModel(),
                        car.getYear(),
                        car.getColor(),
                        ownerEntity.getId()
                ))
                .collect(Collectors.toList());
    }

    private OwnerDTO mapToDTO(Owner owner) {
        List<Long> carIds = owner.getCars() != null
                ? owner.getCars().stream().map(Car::getId).collect(Collectors.toList())
                : List.of();

        return new OwnerDTO(
                owner.getId(),
                owner.getName(),
                owner.getEmail(),
                owner.getPhone(),
                carIds
        );
    }
}