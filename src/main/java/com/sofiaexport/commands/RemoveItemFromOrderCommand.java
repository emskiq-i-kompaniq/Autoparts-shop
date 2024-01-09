package com.sofiaexport.commands;

import lombok.Value;

@Value
public class RemoveItemFromOrderCommand {
    private final Long userId;
    private final Long autoPartId;
}