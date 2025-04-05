package com.example.reservation_movies_app.repository;

import com.example.reservation_movies_app.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    boolean existsByTitle(String title);
}
