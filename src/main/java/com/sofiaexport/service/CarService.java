package com.sofiaexport.service;


import com.sofiaexport.commands.AddCarCommand;
import com.sofiaexport.model.Car;
import com.sofiaexport.repository.CarRepository;
import com.sofiaexport.response.CarResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;

    public List<Car> findAllById(Set<String> ids) {
        return carRepository.findAllById(ids);
    }

    public CarResponse addCar(AddCarCommand carToAdd) {
        Car car = Car.builder()
                .engine(carToAdd.getEngine())
                .brand(carToAdd.getBrand())
                .model(carToAdd.getModel())
                .build();
        String id = carRepository.save(car).getId();
        return CarResponse.builder().id(id).build();
    }
}
