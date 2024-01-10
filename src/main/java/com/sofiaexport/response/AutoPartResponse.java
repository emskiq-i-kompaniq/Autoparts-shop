package com.sofiaexport.response;

import com.sofiaexport.model.AutoPart;
import com.sofiaexport.model.Car;
import com.sofiaexport.model.PartType;
import lombok.Builder;
import lombok.Value;

import java.util.Set;
import java.util.stream.Collectors;

@Value
@Builder
public class AutoPartResponse {
    private final String id;
    private final String brand;
    private final String description;
    private final Double price;
    private final Integer countInStockItems;
    private final PartType partType;
    private final Set<String> compatibleCarsIds;

    public static AutoPartResponse from(AutoPart autoPart) {
        Set<String> compatibleCarsIds = autoPart.getCompatibleCars()
                .stream()
                .map(Car::getId)
                .collect(Collectors.toSet());

        return AutoPartResponse.builder()
                .id(autoPart.getId())
                .brand(autoPart.getBrand())
                .description(autoPart.getDescription())
                .price(autoPart.getPrice())
                .countInStockItems(autoPart.getCountInStockItems())
                .partType(autoPart.getPartType())
                .compatibleCarsIds(compatibleCarsIds)
                .build();
    }
}
