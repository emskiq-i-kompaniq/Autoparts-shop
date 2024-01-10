package com.sofiaexport.controller;

import com.sofiaexport.commands.AddUserCommand;
import com.sofiaexport.dto.UserDto;
import com.sofiaexport.response.UserResponse;
import com.sofiaexport.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Users")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @GetMapping(path = "/v1/users")
    public List<UserDto> getUsers() {
        return userService.getUsers()
                .stream()
                .map(UserDto::fromUser) // Using a static method in UserDto to convert User to UserDto
                .toList();
    }

    @PostMapping(path = "/v1/register")
    public String registerUser(@RequestBody final AddUserCommand user) {
        return userService.registerNewUser(user);
    }
}