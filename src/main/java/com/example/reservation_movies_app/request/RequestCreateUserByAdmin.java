package com.example.reservation_movies_app.request;

import com.example.reservation_movies_app.enums.Role;
import lombok.Data;

@Data
public class RequestCreateUserByAdmin {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
}
