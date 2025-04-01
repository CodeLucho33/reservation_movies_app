package com.example.reservation_movies_app.request;

import lombok.Data;

@Data
public class RequestCreateUserByGuest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;

}
