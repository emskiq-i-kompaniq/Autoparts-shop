package com.sofiaexport.service;

import com.sofiaexport.commands.AddCarCommand;
import com.sofiaexport.model.Car;
import com.sofiaexport.repository.CarRepository;
import com.sofiaexport.response.CarResponse;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService;

    @Test
    void findAllById_ShouldReturnListOfCars() {
        // Arrange
        Set<String> carIds = Set.of("CarId1", "CarId2");
        List<Car> expectedCars = List.of(
                new Car("CarId1", "Brand1", "Model1", "Engine1", Collections.emptySet()),
                new Car("CarId2", "Brand2", "Model2", "Engine2", Collections.emptySet())
        );

        when(carRepository.findAllById(ArgumentMatchers.any())).thenReturn(expectedCars);

        // Act
        List<Car> result = carService.findAllById(carIds);

        // Assert
        assertEquals(expectedCars, result);
        verify(carRepository, times(1)).findAllById(carIds);
    }

    @Test
    void addCar_ShouldReturnCarResponse() {
        // Arrange
        AddCarCommand addCarCommand = new AddCarCommand("Brand", "Model", "Engine");

        Car savedCar = new Car("CarId", "Brand", "Model", "Engine", Collections.emptySet());
        when(carRepository.save(Mockito.any())).thenReturn(savedCar);

        // Act
        CarResponse result = carService.addCar(addCarCommand);

        // Assert
        assertEquals(savedCar.getId(), result.getId());
        verify(carRepository, times(1)).save(Mockito.any());
    }
}

