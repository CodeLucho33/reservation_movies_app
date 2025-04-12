package com.example.reservation_movies_app.service.reservation;


import com.example.reservation_movies_app.dto.ReservationDto;
import com.example.reservation_movies_app.exception.ResourceNotFoundException;
import com.example.reservation_movies_app.model.Reservation;
import com.example.reservation_movies_app.model.ShowTime;
import com.example.reservation_movies_app.model.User;
import com.example.reservation_movies_app.repository.ReservationRepository;
import com.example.reservation_movies_app.repository.ShowTimeRespository;
import com.example.reservation_movies_app.repository.UserRepository;
import com.example.reservation_movies_app.request.CreateReservationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService implements IReservationService {

    private final ReservationRepository reservationRepository;
    private final ShowTimeRespository showTimeRespository;
    private final UserRepository userRepository;

    @Override
    public Reservation createReservation(CreateReservationRequest request) {
        ShowTime showTime = showTimeRespository.findById(request.getShowTimeId())
                .orElseThrow(() -> new ResourceNotFoundException("Show time not found"));

        if (showTime.getNumberOfSeats() < request.getNumberOfSeats()) {
            throw new ResourceNotFoundException("Number of seats exceeds number of seats");
        }

        User user = userRepository.findById(request.getShowTimeId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setShowTime(showTime);
        reservation.setQuantity(request.getNumberOfSeats());
        reservation.setReservationTime(LocalDateTime.now());

        //Restamos los asientos disponibles
        showTime.setNumberOfSeats(showTime.getNumberOfSeats() - request.getNumberOfSeats());
        return reservationRepository.save(reservation);
    }

    @Override
    public List<ReservationDto> getReservationsByUser(Long userId) {
        List<Reservation> reservations = reservationRepository.findByUserId(userId);
        return getConvertedReservationsToDto(reservations);
    }

    @Override
    public ReservationDto convertReservationToDto(Reservation reservation) {

        ReservationDto dto = new ReservationDto();
        dto.setId(reservation.getId());
        dto.setMovieTitle(reservation.getShowTime().getMovie().getTitle());
        dto.setUserEmail(reservation.getUser().getEmail());
        dto.setShowTime(reservation.getShowTime().getTime());
        dto.setNumberOfSeats(reservation.getQuantity());
        dto.setReservationTime(reservation.getReservationTime());
        return dto;

    }

    @Override
    public List<ReservationDto> getConvertedReservationsToDto(List<Reservation> reservations) {
        return reservations.stream()
                .map(this::convertReservationToDto)
                .collect(Collectors.toList());
    }
}
