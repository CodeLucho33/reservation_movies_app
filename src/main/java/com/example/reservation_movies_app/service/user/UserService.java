package com.example.reservation_movies_app.service.user;

import com.example.reservation_movies_app.dto.UserDto;
import com.example.reservation_movies_app.enums.Role;
import com.example.reservation_movies_app.exception.ResourceNotFoundException;
import com.example.reservation_movies_app.model.User;
import com.example.reservation_movies_app.repository.UserRepository;
import com.example.reservation_movies_app.request.RequestCreateUserByAdmin;
import com.example.reservation_movies_app.request.RequestCreateUserByGuest;
import com.example.reservation_movies_app.request.RequestUpdateUserLikeAdmin;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User not found!"));

    }

    @Override
    public User createUserLikeAdmin(RequestCreateUserByAdmin request) {
        return Optional.of(request)
                .filter(user ->!userRepository.existsByEmail(request.getEmail()))
                .map(req ->{
                    User user = new User();
                    user.setEmail(request.getEmail());
                    user.setPassword(passwordEncoder.encode(request.getPassword()));
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    user.setRole(req.getRole());
                    return userRepository.save(user);

                }).orElseThrow(()-> new IllegalArgumentException("User already exists!"));
    }

    @Override
    public User updateUserLikeAdmin(RequestUpdateUserLikeAdmin request, Long userId) {
return userRepository.findById(userId)
        .map(existingUser ->{
            existingUser.setEmail(request.getEmail());
            existingUser.setFirstName(request.getFirstName());
            existingUser.setLastName(request.getLastName());
            existingUser.setRole(request.getRole());
            // ✅ Solo cambiar la contraseña si se envió una nueva
            if (request.getPassword() != null && !request.getPassword().isEmpty()) {
                existingUser.setPassword(passwordEncoder.encode(request.getPassword()));
            }

            return userRepository.save(existingUser);
        }).orElseThrow(()-> new ResourceNotFoundException("User not found!"));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId)
                .ifPresentOrElse(userRepository::delete, () -> {
                    throw new ResourceNotFoundException("User not found!");
                });
    }

    @Override
    public UserDto convertUserToUserDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new ResourceNotFoundException("No authenticated user found!");
        }
        String email = authentication.getName();
        return userRepository.findByEmail(email);
    }

    @Override
    public User createUserLikeGuest(RequestCreateUserByGuest request) {
        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setRole(Role.REGULAR);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<UserDto> getConvertedUsers(List<User> users) {
        return users.stream().map(this::convertUserToUserDto).toList();
    }
}
