package com.example.reservation_movies_app.controller;

import com.example.reservation_movies_app.request.LoginRequest;
import com.example.reservation_movies_app.response.ApiResponse;
import com.example.reservation_movies_app.response.JwtResponse;
import com.example.reservation_movies_app.security.jwt.JwtUtils;
import com.example.reservation_movies_app.security.user.MovieUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/auth")

    public class AuthController {
        private final AuthenticationManager authenticationManager;
        private final JwtUtils jwtUtils;


        @PostMapping("/login")
        public ResponseEntity<ApiResponse>  login(@Valid @RequestBody LoginRequest request) {

            try {
                Authentication authentication = authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(
                                request.getEmail(), request.getPassword()));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                String jwt = jwtUtils.generateTokenForUser(authentication);
               MovieUserDetails userDetails = (MovieUserDetails) authentication.getPrincipal();
                JwtResponse jwtResponse = new JwtResponse(userDetails.getId(),jwt);
                return ResponseEntity.ok(new ApiResponse("Login Successful", jwtResponse));
            }catch (AuthenticationException e) {

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse( e.getMessage(), null));
            }

        }

    }






