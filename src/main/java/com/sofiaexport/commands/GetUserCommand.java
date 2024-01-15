package com.sofiaexport.commands;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetUserCommand {
    private final String id;
}
