package com.example.reservation_movies_app.service.reservation;

import com.example.reservation_movies_app.dto.ReservationDto;
import com.example.reservation_movies_app.model.Reservation;
import com.example.reservation_movies_app.request.CreateReservationRequest;

import java.util.List;

public interface IReservationService {

    Reservation createReservation(CreateReservationRequest request);
    List<ReservationDto> getReservationsByUser(Long userId);
    ReservationDto convertReservationToDto(Reservation reservation);
    List<ReservationDto> getConvertedReservationsToDto(List<Reservation> reservations);

}
