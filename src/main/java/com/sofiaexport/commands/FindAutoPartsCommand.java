package com.sofiaexport.commands;

import com.sofiaexport.model.PartType;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public final class FindAutoPartsCommand {
    private final String brand;
    private final PartType partType;
    private final Double price;
}
