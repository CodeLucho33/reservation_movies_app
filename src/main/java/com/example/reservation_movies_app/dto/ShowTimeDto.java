package com.example.reservation_movies_app.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShowTimeDto {
    private LocalDateTime time;
    private int numberOfSeats;
    private String movieTitle;
}
