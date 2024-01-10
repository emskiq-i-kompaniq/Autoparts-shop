package com.sofiaexport.service;


import com.sofiaexport.model.Car;
import com.sofiaexport.repository.CarRepository;
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
}
