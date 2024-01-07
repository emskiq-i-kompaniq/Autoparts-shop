package com.sofiaexport.controller;

import com.sofiaexport.model.User;
import com.sofiaexport.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/v1/users")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @PostMapping(path = "/v1/register")
    public void registerUser(@RequestBody User user) {
        userService.registerNewUser(user);
    }
}