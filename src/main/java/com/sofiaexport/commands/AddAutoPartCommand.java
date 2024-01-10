package com.sofiaexport.commands;

import com.sofiaexport.model.AutoPart;
import com.sofiaexport.model.PartType;
import lombok.Value;

import java.util.Set;

@Value
public class AddAutoPartCommand {
    private final String brand;
    private final String description;
    private final String serialNumber;
    private final Integer countInStockItems;
    private final PartType partType;
    private final Double price;
    private final Set<String> compatibleCarsIds;

    public AutoPart toAutoPart() {
        return new AutoPart(brand, partType, description, price, serialNumber, countInStockItems);
    }
}
