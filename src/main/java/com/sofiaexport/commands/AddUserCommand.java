package com.sofiaexport.commands;

public record AddUserCommand(
        String name,
        String email,
        String password
) { }
