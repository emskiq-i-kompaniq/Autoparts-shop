package com.sofiaexport.controller;

import com.sofiaexport.commands.AddCarCommand;
import com.sofiaexport.response.CarResponse;
import com.sofiaexport.service.CarService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Cars")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CarController {

    private final CarService carService;

    @PostMapping(path = "v1/add-car")
    public ResponseEntity<CarResponse> addCar(@RequestBody AddCarCommand command) {
        return ResponseEntity.ok(carService.addCar(command));
    }
}
