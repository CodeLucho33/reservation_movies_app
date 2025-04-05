package com.example.reservation_movies_app.service.movie;

import com.example.reservation_movies_app.dto.MovieDto;
import com.example.reservation_movies_app.model.Movie;
import com.example.reservation_movies_app.request.CreateMovieRequest;

import java.util.List;

public interface IMovieService {
    Movie getMovieById(Long movieId);
    Movie createMovie(CreateMovieRequest request);
    Movie updateMovie(CreateMovieRequest request, Long movieId);
    void deleteMovie(Long movieId);
    MovieDto convertMovieToDto(Movie movie);
    List<Movie> getAllMovies();
    List<MovieDto> getConvertedMovies(List<Movie> movies);
}
