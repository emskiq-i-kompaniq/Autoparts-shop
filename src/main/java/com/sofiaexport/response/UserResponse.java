package com.sofiaexport.response;

import com.sofiaexport.model.User;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserResponse {
    private final String id;
    private final String name;
    private final String email;

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }
}
