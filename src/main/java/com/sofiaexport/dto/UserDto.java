package com.sofiaexport.dto;

import com.sofiaexport.model.User;
import lombok.Data;

@Data
public class UserDto {
    private String id;
    private String name;
    private String email;

    public static UserDto fromUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());

        return userDto;
    }
}
