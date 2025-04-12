package com.example.reservation_movies_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ReservationDto {
    private Long id;
    private String userEmail;
    private String movieTitle;
    private LocalDateTime showTime;
    private int numberOfSeats;
    private LocalDateTime reservationTime;
}
