package com.sofiaexport.commands;

import lombok.Value;

@Value
public class AddItemToOrderCommand {
    private final String userId;
    private final String autoPartId;
}
