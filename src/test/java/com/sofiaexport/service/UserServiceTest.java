package com.sofiaexport.service;

import com.sofiaexport.commands.AddUserCommand;
import com.sofiaexport.commands.AuthenticationCommand;
import com.sofiaexport.model.Role;
import com.sofiaexport.model.User;
import com.sofiaexport.repository.TokenRepository;
import com.sofiaexport.repository.UserRepository;
import com.sofiaexport.response.AuthenticationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void registerNewUser_Success() {
        // Arrange
        AddUserCommand userCommand = new AddUserCommand("John Doe", "john@example.com", "password", Role.USER);
        User mockedUser = User.builder().id("1").name(userCommand.name()).email(userCommand.email()).password("encodedPassword").role(Role.USER).build();
        when(userRepository.findUserByEmail(any())).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(mockedUser);
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");

        // Act
        String userId = userService.registerNewUser(userCommand);

        // Assert
        assertNotNull(userId);
        assertEquals("1", userId);
        verify(userRepository, times(1)).findUserByEmail(userCommand.email());
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void registerNewUser_UserAlreadyExists_ExceptionThrown() {
        // Arrange
        AddUserCommand userCommand = new AddUserCommand("John Doe", "john@example.com", "password", Role.USER);
        when(userRepository.findUserByEmail(any())).thenReturn(Optional.of(new User()));

        // Act and Assert
        assertThrows(IllegalStateException.class, () -> userService.registerNewUser(userCommand));
        verify(userRepository, times(1)).findUserByEmail(userCommand.email());
        verify(userRepository, never()).save(any());
        verify(passwordEncoder, never()).encode(any());
    }

    @Test
    void authenticate_Success() {
        // Arrange
        AuthenticationCommand authCommand = new AuthenticationCommand("john@example.com", "password");
        User mockedUser = User.builder().id("1").name("John Doe").email("john@example.com").password("encodedPassword").role(Role.USER).build();
        AuthenticationResponse mockedResponse = AuthenticationResponse.builder().accessToken("jwtToken").userId("1").build();
        when(userRepository.findUserByEmail(any())).thenReturn(Optional.of(mockedUser));
        when(jwtService.generateToken(any())).thenReturn("jwtToken");

        // Act
        AuthenticationResponse response = userService.authenticate(authCommand);

        // Assert
        assertNotNull(response);
        assertEquals("jwtToken", response.getAccessToken());
        assertEquals("1", response.getUserId());
        verify(userRepository, times(1)).findUserByEmail(authCommand.getEmail());
        verify(jwtService, times(1)).generateToken(mockedUser);
    }

}
