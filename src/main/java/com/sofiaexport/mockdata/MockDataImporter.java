package com.sofiaexport.mockdata;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sofiaexport.commands.AddAutoPartCommand;
import com.sofiaexport.commands.AddCarCommand;
import com.sofiaexport.commands.AddUserCommand;
import com.sofiaexport.commands.FindAutoPartsCommand;
import com.sofiaexport.model.AutoPart;
import com.sofiaexport.model.Role;
import com.sofiaexport.repository.AutoPartRepository;
import com.sofiaexport.service.AutoPartService;
import com.sofiaexport.service.CarService;
import com.sofiaexport.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class MockDataImporter implements CommandLineRunner {
    private final Gson gson;
    private final UserService userService;
    private final CarService carService;
    private final AutoPartService autoPartService;

    private final static String USERS_DATA_FILE_PATH = "src/main/resources/mockdata/users.json";
    private final static String CARS_DATA_FILE_PATH = "src/main/resources/mockdata/cars.json";
    private final static String PARTS_DATA_FILE_PATH = "src/main/resources/mockdata/autoparts.json";

    @Override
    public void run(String... args) {
        List<AddCarCommand> mockCars = readCarDataFromJson();
        mockCars.forEach(carService::addCar);

        List<AddUserCommand> mockUsers = readUserDataFromJson()
                .stream()
                .map(RegisterUserCommand::toModel)
                .toList();

        mockUsers.forEach(userService::register);

        List<AddAutoPartCommand> mockAutoParts = readAutoPartsDataFromJson();
        mockAutoParts.forEach(autopart -> {
            Set<String> compatibleCars = findCompatibleCarsIds(autopart.getBrand());
            AddAutoPartCommand updatedAutopartCommand = autopart.toBuilder().compatibleCarsIds(compatibleCars).build();
            autoPartService.saveAutoPart(updatedAutopartCommand);
        });
    }

    private List<RegisterUserCommand> readUserDataFromJson() {
        try (FileReader reader = new FileReader(USERS_DATA_FILE_PATH)) {
            TypeToken<List<RegisterUserCommand>> typeToken = new TypeToken<>() {};

            return gson.fromJson(reader, typeToken.getType());
        } catch (Exception e) {
            log.error("Error deserializing json", e);
        }
        return Collections.emptyList();
    }

    private List<AddCarCommand> readCarDataFromJson() {
        try (FileReader reader = new FileReader(CARS_DATA_FILE_PATH)) {
            TypeToken<List<AddCarCommand>> typeToken = new TypeToken<>() {};

            return gson.fromJson(reader, typeToken.getType());
        } catch (Exception e) {
            log.error("Error deserializing json", e);
        }
        return Collections.emptyList();
    }

    private List<AddAutoPartCommand> readAutoPartsDataFromJson() {
        try (FileReader reader = new FileReader(PARTS_DATA_FILE_PATH)) {
            TypeToken<List<AddAutoPartCommand>> typeToken = new TypeToken<>() {};

            return gson.fromJson(reader, typeToken.getType());
        } catch (Exception e) {
            log.error("Error deserializing json", e);
        }
        return Collections.emptyList();
    }

    private Set<String> findCompatibleCarsIds(String brand) {
        FindAutoPartsCommand command = FindAutoPartsCommand.builder().brand(brand).build();
        return autoPartService.findAutoParts(command)
                .stream()
                .map(AutoPart::getId)
                .collect(Collectors.toSet());
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
