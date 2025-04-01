package com.example.reservation_movies_app.controller;

import com.example.reservation_movies_app.dto.UserDto;
import com.example.reservation_movies_app.exception.AlreadyExistsException;
import com.example.reservation_movies_app.exception.ResourceNotFoundException;
import com.example.reservation_movies_app.model.User;
import com.example.reservation_movies_app.request.RequestCreateUserByAdmin;
import com.example.reservation_movies_app.request.RequestUpdateUserLikeAdmin;
import com.example.reservation_movies_app.response.ApiResponse;
import com.example.reservation_movies_app.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/users")
public class UserController {
    private final IUserService userService;

    /*Search user by id*/
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId) {
        try {
            User user = userService.getUserById(userId);
            UserDto userDto = userService.convertUserToUserDto(user);
            return ResponseEntity.ok(new ApiResponse("Success search user", userDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("User not found", null));
        }
    }
    /*Add user to bd*/
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse> createUser(@RequestBody RequestCreateUserByAdmin request) {
        try {
            User user =userService.createUserLikeAdmin(request);
            UserDto userDto = userService.convertUserToUserDto(user);
            return ResponseEntity.ok(new ApiResponse("Success Created User", userDto));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("User already exists", null));
        }
    }
    /*update user*/
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody RequestUpdateUserLikeAdmin request, @PathVariable Long userId) {
        try {
            User user = userService.updateUserLikeAdmin(request, userId);
            UserDto userDto = userService.convertUserToUserDto(user);
            return ResponseEntity.ok(new ApiResponse("Success Updated User", userDto));
        } catch (ResourceNotFoundException e) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("User not found", null));
        }

    }
    /*delete user*/
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok(new ApiResponse("Success Deleted User", null));
        } catch (ResourceNotFoundException e) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("User not found", null));
        }
    }

    /*get all users*/

}
