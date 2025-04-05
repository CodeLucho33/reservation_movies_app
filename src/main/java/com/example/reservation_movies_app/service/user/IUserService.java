package com.example.reservation_movies_app.service.user;

import com.example.reservation_movies_app.dto.UserDto;
import com.example.reservation_movies_app.model.User;
import com.example.reservation_movies_app.request.RequestCreateUserByAdmin;
import com.example.reservation_movies_app.request.RequestCreateUserByGuest;
import com.example.reservation_movies_app.request.RequestUpdateUserLikeAdmin;

import java.util.List;

public interface IUserService {
    User getUserById(Long userId);
    User createUserLikeAdmin(RequestCreateUserByAdmin request);
    User updateUserLikeAdmin(RequestUpdateUserLikeAdmin request, Long userId);
    void deleteUser(Long id);
    UserDto convertUserToUserDto(User user);
    User getAuthenticatedUser();
    User createUserLikeGuest( RequestCreateUserByGuest request);
    List<User> getAllUsers();
    List<UserDto> getConvertedUsers(List<User> users);
}
