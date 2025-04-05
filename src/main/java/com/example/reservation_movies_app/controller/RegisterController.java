package com.example.reservation_movies_app.controller;

import com.example.reservation_movies_app.dto.UserDto;
import com.example.reservation_movies_app.exception.AlreadyExistsException;
import com.example.reservation_movies_app.model.User;
import com.example.reservation_movies_app.request.RequestCreateUserByGuest;
import com.example.reservation_movies_app.response.ApiResponse;
import com.example.reservation_movies_app.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/register")
public class RegisterController {
    private final IUserService userService;
    @PostMapping
    public ResponseEntity<ApiResponse> registerUser(@RequestBody RequestCreateUserByGuest request) {
        try {
            User user = userService.createUserLikeGuest(request);
            UserDto  userDto = userService.convertUserToUserDto(user);
            return ResponseEntity.ok(new ApiResponse("Success Create User", userDto));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("User already exists", null));
        }
    }
}
