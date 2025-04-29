package ch.example.springgraphqlkeycloakboilerplate.repository;

import ch.example.springgraphqlkeycloakboilerplate.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByOwnerId(Long ownerId);
}