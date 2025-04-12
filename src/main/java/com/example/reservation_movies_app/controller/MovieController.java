package com.example.reservation_movies_app.controller;

import com.example.reservation_movies_app.dto.MovieDto;
import com.example.reservation_movies_app.exception.AlreadyExistsException;
import com.example.reservation_movies_app.exception.ResourceNotFoundException;
import com.example.reservation_movies_app.model.Movie;
import com.example.reservation_movies_app.request.CreateMovieRequest;
import com.example.reservation_movies_app.response.ApiResponse;
import com.example.reservation_movies_app.service.movie.IMovieService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/movies")
public class MovieController {
    private final IMovieService movieService;
    /*Search movie by id
    * Required login: ADMIN
    * */

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{movieId}")
    public ResponseEntity<ApiResponse> getMovieById(@PathVariable Long movieId) {
        try {
            Movie movie = movieService.getMovieById(movieId);
            MovieDto movieDto = movieService.convertMovieToDto(movie);
            return ResponseEntity.ok(new ApiResponse("Success search movie", movieDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Movie not found", null));
        }
    }

    /*Create movie
    * * Required login: ADMIN
    * */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse> createMovie(@RequestBody CreateMovieRequest request) {
        try {
            Movie movie = movieService.createMovie(request);
            MovieDto movieDto = movieService.convertMovieToDto(movie);
            return ResponseEntity.ok(new ApiResponse("Success created movie", movieDto));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("Movie already exists", null));
        }
    }

    /* Update movie
    * * Required login: ADMIN
    * */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{movieId}")
    public ResponseEntity<ApiResponse> updateMovie(@PathVariable Long movieId, @RequestBody CreateMovieRequest request) {
        try {
            Movie movie = movieService.updateMovie(request, movieId);
            MovieDto movieDto = movieService.convertMovieToDto(movie);
            return ResponseEntity.ok(new ApiResponse("Success updated movie", movieDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Movie not found", null));
        }
    }

    /* Delete movie
    * * Required login: ADMIN
    * */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{movieId}")
    public ResponseEntity<ApiResponse> deleteMovie(@PathVariable Long movieId) {
        try {
            movieService.deleteMovie(movieId);
            return ResponseEntity.ok(new ApiResponse("Success deleted movie", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Movie not found", null));
        }
    }

    /*Get all movies
    * Required login : REGULAR*/
    @PreAuthorize("hasRole('REGULAR')")
    @GetMapping
    public ResponseEntity<ApiResponse> getAllMovies() {
        List<Movie> movie = movieService.getAllMovies();
        List<MovieDto> convertMovies = movieService.getConvertedMovies(movie);
        return ResponseEntity.ok(new ApiResponse("Success retrieved movies", convertMovies));
    }

    
    
    
    
    
    
    
    
    




}
