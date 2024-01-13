package com.sofiaexport.commands;

import com.sofiaexport.model.Role;

public record AddUserCommand(
        String name,
        String email,
        String password,

        Role role
) { }
