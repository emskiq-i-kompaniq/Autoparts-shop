package com.sofiaexport.service;

import com.sofiaexport.commands.AddUserCommand;
import com.sofiaexport.exception.UserNotFoundException;
import com.sofiaexport.model.User;
import com.sofiaexport.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public String registerNewUser(AddUserCommand user) {
        if (userRepository.findUserByEmail(user.email()).isPresent()) {
            throw new IllegalStateException("User already added");
        }
        User userToAdd = new User(user.name(), user.email(), user.password());
        return userRepository.save(userToAdd).getId();
    }

    public User findUserById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }
}
