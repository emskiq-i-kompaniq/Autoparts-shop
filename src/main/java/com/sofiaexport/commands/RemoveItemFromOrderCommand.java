package com.sofiaexport.commands;

import lombok.Value;

@Value
public class RemoveItemFromOrderCommand {
    private final String userId;
    private final String autoPartId;
}