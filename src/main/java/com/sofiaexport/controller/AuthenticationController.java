package com.sofiaexport.controller;

import com.sofiaexport.commands.AddUserCommand;
import com.sofiaexport.commands.AuthenticationCommand;
import com.sofiaexport.response.AuthenticationResponse;
import com.sofiaexport.response.UserResponse;
import com.sofiaexport.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Authentication")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final UserService userService;

    @GetMapping(path = "/users")
    public List<UserResponse> getUsers() {
        return userService.getUsers()
                .stream()
                .map(UserResponse::from) // Using a static method in UserDto to convert User to UserDto
                .toList();
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationCommand request
    ) {
        return ResponseEntity.ok(userService.authenticate(request));
    }

    @PostMapping(path = "/register")
    public ResponseEntity<AuthenticationResponse> registerUser(@RequestBody final AddUserCommand user) {
        return ResponseEntity.ok(userService.register(user));
    }
}