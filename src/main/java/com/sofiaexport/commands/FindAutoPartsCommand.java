package com.sofiaexport.commands;

import com.sofiaexport.model.PartType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public final class FindAutoPartsCommand {
    private final String brand;
    private final PartType partType;
    private final Double price;
}
