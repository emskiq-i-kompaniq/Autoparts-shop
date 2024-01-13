package com.sofiaexport.mockdata;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sofiaexport.commands.AddUserCommand;
import com.sofiaexport.model.Role;
import com.sofiaexport.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class MockDataImporter implements CommandLineRunner {
    private final Gson gson;
    private final UserService userService;

    private final static String USERS_DATE_FILE_PATH = "src/main/resources/mockdata/users.json";

    @Override
    public void run(String... args) {
        List<AddUserCommand> userResponses = readUserDataFromJson()
                .stream()
                .map(RegisterUserCommand::toModel)
                .toList();

        userResponses.parallelStream().forEach(userService::register);
    }

    private List<RegisterUserCommand> readUserDataFromJson() {
        try (FileReader reader = new FileReader(USERS_DATE_FILE_PATH)) {
            TypeToken<List<RegisterUserCommand>> typeToken = new TypeToken<>() {};

            return gson.fromJson(reader, typeToken.getType());
        } catch (Exception e) {
            log.error("Error deserializing json", e);
        }
        return Collections.emptyList();
    }


    @Data
    private class RegisterUserCommand {
        private String name;
        private String email;
        private String password;
        private Role role;

        public AddUserCommand toModel() {
            return new AddUserCommand(name, email, password, role);
        }
    }
}
