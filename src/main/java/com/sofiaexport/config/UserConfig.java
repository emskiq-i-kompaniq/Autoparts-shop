package com.sofiaexport.config;

import com.sofiaexport.model.*;
import com.sofiaexport.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class UserConfig {

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository) {
        return args -> {
            User pavlin = new User("Pavlin", "pavlindimitrov12@gmail.com", "password123");
            AutoPart oilFilter = new AutoPart("Mann", PartType.FILTER, "maslen filtur", 25.50, "1234", 4);
            UserOrder firstOrder = new UserOrder(OrderStatus.PENDING, 152.00, 2L);
            userRepository.saveAll(List.of(pavlin));
        };
    }
}
