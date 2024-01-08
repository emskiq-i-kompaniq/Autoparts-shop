package com.sofiaexport.commands;

import lombok.Value;

@Value
public class AddItemToOrderCommand {
    private final Long userId;
    private final Long autoPartId;
}
