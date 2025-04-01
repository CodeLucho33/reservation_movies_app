package com.example.reservation_movies_app.dto;

import com.example.reservation_movies_app.enums.Role;
import lombok.Data;

@Data
public class UserDto {
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
}
