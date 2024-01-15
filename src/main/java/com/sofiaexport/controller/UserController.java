package com.sofiaexport.controller;

import com.sofiaexport.model.User;
import com.sofiaexport.response.UserResponse;
import com.sofiaexport.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api")
@Tag(name = "Users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/v1/user/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String userId) {
        Optional<User> user = userService.getUserById(userId);
        return user.map(value -> ResponseEntity.ok(UserResponse.from(value))).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }
}
