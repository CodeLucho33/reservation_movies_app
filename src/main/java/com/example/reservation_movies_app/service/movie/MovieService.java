package com.example.reservation_movies_app.service.movie;

import com.example.reservation_movies_app.dto.MovieDto;
import com.example.reservation_movies_app.exception.AlreadyExistsException;
import com.example.reservation_movies_app.exception.ResourceNotFoundException;
import com.example.reservation_movies_app.model.Movie;
import com.example.reservation_movies_app.repository.MovieRepository;
import com.example.reservation_movies_app.request.CreateMovieRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService implements IMovieService {

    private final MovieRepository movieRepository;
    private final ModelMapper modelMapper;



    @Override
    public Movie getMovieById(Long movieId) {
        return movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found!"));
    }

    @Override
    public Movie createMovie(CreateMovieRequest request) {
        return Optional.of(request)
                .filter(movie -> !movieRepository.existsByTitle(request.getTitle()))
                .map(req -> {
                    Movie movie = new Movie();
                    movie.setTitle(request.getTitle());
                    movie.setDescription(request.getDescription());
                    movie.setYear(request.getYear());
                    movie.setPosterPath(request.getPosterPath());
                    movie.setGenreMovie(request.getGenreMovie());
                    //movie.setShowTimes(request.getShowTimes());
                    return movieRepository.save(movie);
                }).orElseThrow(()-> new AlreadyExistsException("Movie already exists"));
    }

    @Override
    public Movie updateMovie(CreateMovieRequest request, Long movieId) {
        return movieRepository.findById(movieId)
                .map(existingMovie ->{
                    existingMovie.setTitle(request.getTitle());
                    existingMovie.setYear(request.getYear());
                    existingMovie.setDescription(request.getDescription());
                    existingMovie.setPosterPath(request.getPosterPath());
                    existingMovie.setGenreMovie(request.getGenreMovie());
                   // existingMovie.setShowTimes(request.getShowTimes());
                    return movieRepository.save(existingMovie);
                }).orElseThrow(()-> new ResourceNotFoundException("Movie not found!"));

    }

    @Override
    public void deleteMovie(Long movieId) {
        movieRepository.findById(movieId)
                .ifPresentOrElse(movieRepository::delete, () -> {
                    throw new ResourceNotFoundException("Movie not found!");
                });
    }

    @Override
    public MovieDto convertMovieToDto(Movie movie) {
        return modelMapper.map(movie, MovieDto.class);
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public List<MovieDto> getConvertedMovies(List<Movie> movies) {
        return movies.stream().map(this::convertMovieToDto).toList();
    }
}
