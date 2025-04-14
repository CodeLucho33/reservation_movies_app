package com.example.reservation_movies_app.controller;

import com.example.reservation_movies_app.dto.ReservationDto;
import com.example.reservation_movies_app.exception.AlreadyExistsException;
import com.example.reservation_movies_app.exception.ResourceNotFoundException;
import com.example.reservation_movies_app.model.Reservation;
import com.example.reservation_movies_app.request.CreateReservationRequest;
import com.example.reservation_movies_app.response.ApiResponse;
import com.example.reservation_movies_app.service.reservation.IReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/reservation")
public class ReservationController {
    private final IReservationService reservationService;

    @PreAuthorize("hasRole('ROLE_REGULAR')")
    @PostMapping
    public ResponseEntity<ApiResponse> createReservation(@RequestBody CreateReservationRequest request) {
    try {
        Reservation reservation = reservationService.createReservation(request);
        ReservationDto reservationDto = reservationService.convertReservationToDto(reservation);
        return ResponseEntity.ok(new ApiResponse("Success creating reservation", reservationDto));
    } catch (AlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("Reservation already exists", null));
    }
}
@PreAuthorize("hasRole('ROLE_REGULAR')")
@GetMapping("/{userId}")
public ResponseEntity<ApiResponse> getReservationsByUser(@PathVariable Long userId) {
    try {
        List<ReservationDto> reservations = reservationService.getReservationsByUser(userId);
        return ResponseEntity.ok(new ApiResponse("Success retried reservations", reservations));
    } catch (ResourceNotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Reservations not found for user:", null));
    }
}
}
