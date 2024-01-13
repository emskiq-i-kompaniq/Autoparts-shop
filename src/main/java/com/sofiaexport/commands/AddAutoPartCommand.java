package com.sofiaexport.commands;

import com.sofiaexport.model.AutoPart;
import com.sofiaexport.model.PartType;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder(toBuilder = true)
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
