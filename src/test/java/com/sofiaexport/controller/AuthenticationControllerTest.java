package com.sofiaexport.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sofiaexport.commands.AddUserCommand;
import com.sofiaexport.model.User;
import com.sofiaexport.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.Mockito.*;

@WebMvcTest(AuthenticationController.class)
class AuthenticationControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getUsers_ShouldReturnUserList() throws Exception {
        List<User> userResponses = List.of(
                new User("John Doe", "john@example.com", ""),
                new User("Jane Doe", "jane@example.com", "")
        );

        when(userService.getUsers()).thenReturn(userResponses);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(userResponses.size()));

        verify(userService, times(1)).getUsers();
    }

    @Test
    void registerUser_ShouldReturnSuccessMessage() throws Exception {
        AddUserCommand addUserCommand = new AddUserCommand("John Doe", "john@example.com", "password");

        when(userService.registerNewUser(ArgumentMatchers.any(AddUserCommand.class))).thenReturn("User registered successfully");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addUserCommand)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("User registered successfully"));

        verify(userService, times(1)).registerNewUser(addUserCommand);
    }
}