package ch.example.springgraphqlkeycloakboilerplate.resolver;

import ch.example.springgraphqlkeycloakboilerplate.dto.CarDTO;
import ch.example.springgraphqlkeycloakboilerplate.dto.CarInput;
import ch.example.springgraphqlkeycloakboilerplate.entity.Car;
import ch.example.springgraphqlkeycloakboilerplate.entity.Owner;
import ch.example.springgraphqlkeycloakboilerplate.exception.GraphQLException;
import ch.example.springgraphqlkeycloakboilerplate.repository.CarRepository;
import ch.example.springgraphqlkeycloakboilerplate.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CarResolver {

    private final CarRepository carRepository;
    private final OwnerRepository ownerRepository;

    @Autowired
    public CarResolver(CarRepository carRepository, OwnerRepository ownerRepository) {
        this.carRepository = carRepository;
        this.ownerRepository = ownerRepository;
    }

    @QueryMapping
    public List<CarDTO> cars() {
        return carRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @QueryMapping
    public CarDTO carById(@Argument Long id) {
        return carRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new GraphQLException("Car not found with id: " + id));
    }

    @MutationMapping
    public CarDTO createCar(@Argument CarInput input) {
        Owner owner = ownerRepository.findById(input.getOwnerId())
                .orElseThrow(() -> new GraphQLException("Owner not found with id: " + input.getOwnerId()));

        Car car = new Car();
        car.setModel(input.getModel());
        car.setYear(input.getYear());
        car.setColor(input.getColor());
        car.setOwner(owner);

        return mapToDTO(carRepository.save(car));
    }

    @MutationMapping
    public CarDTO updateCar(@Argument Long id, @Argument CarInput input) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new GraphQLException("Car not found with id: " + id));

        Owner owner = ownerRepository.findById(input.getOwnerId())
                .orElseThrow(() -> new GraphQLException("Owner not found with id: " + input.getOwnerId()));

        car.setModel(input.getModel());
        car.setYear(input.getYear());
        car.setColor(input.getColor());
        car.setOwner(owner);

        return mapToDTO(carRepository.save(car));
    }

    @MutationMapping
    public Boolean deleteCar(@Argument Long id) {
        if (!carRepository.existsById(id)) {
            throw new GraphQLException("Car not found with id: " + id);
        }
        carRepository.deleteById(id);
        return true;
    }

    private CarDTO mapToDTO(Car car) {
        return new CarDTO(
                car.getId(),
                car.getModel(),
                car.getYear(),
                car.getColor(),
                car.getOwner() != null ? car.getOwner().getId() : null
        );
    }
}