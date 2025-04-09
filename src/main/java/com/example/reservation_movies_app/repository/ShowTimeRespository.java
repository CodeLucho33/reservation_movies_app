package com.example.reservation_movies_app.repository;

import com.example.reservation_movies_app.model.ShowTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShowTimeRespository extends JpaRepository<ShowTime, Long> {
    List<ShowTime> findByMovieId(Long movieId);

    List<ShowTime> findByMovieTitleContainingIgnoreCase(String title);
}
