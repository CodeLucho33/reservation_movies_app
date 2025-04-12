package com.example.reservation_movies_app.request;

import lombok.Data;

@Data
public class CreateReservationRequest {
    private Long showTimeId;
    private int numberOfSeats;
}
