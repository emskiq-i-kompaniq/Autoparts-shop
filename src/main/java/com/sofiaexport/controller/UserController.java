package com.sofiaexport.controller;

import com.sofiaexport.commands.AddUserCommand;
import com.sofiaexport.response.UserResponse;
import com.sofiaexport.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @GetMapping(path = "/v1/users")
    public List<UserResponse> getUsers() {
        return userService.getUsers()
                .stream()
                .map(UserResponse::from)
                .toList();
    }

    @PostMapping(path = "/v1/register")
    public String registerUser(@RequestBody final AddUserCommand user) {
        return userService.registerNewUser(user);
    }
}