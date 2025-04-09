package com.example.reservation_movies_app.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class        CreateShowTimeRequest {
    private Long movieId;
    private LocalDateTime time;
    private int numberOfSeats;

}
