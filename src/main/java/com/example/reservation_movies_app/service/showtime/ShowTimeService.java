package com.example.reservation_movies_app.service.showtime;

import com.example.reservation_movies_app.dto.ShowTimeDto;
import com.example.reservation_movies_app.exception.ResourceNotFoundException;
import com.example.reservation_movies_app.model.Movie;
import com.example.reservation_movies_app.model.ShowTime;
import com.example.reservation_movies_app.repository.MovieRepository;
import com.example.reservation_movies_app.repository.ShowTimeRespository;
import com.example.reservation_movies_app.request.CreateShowTimeRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShowTimeService implements IShowTimeService {

    private final MovieRepository movieRepository;
    private final ShowTimeRespository showTimeRespository;

    @Override
    public ShowTime createShowTime(CreateShowTimeRequest request) {

        Movie movie = movieRepository.findById(request.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));

        ShowTime showTime = new ShowTime();
        showTime.setMovie(movie);
        showTime.setTime(request.getTime());
        showTime.setNumberOfSeats(request.getNumberOfSeats());

        return showTimeRespository.save(showTime);
    }

    @Override
    public void deleteShowTime(Long showId) {
        showTimeRespository.findById(showId)
                .ifPresentOrElse(showTimeRespository::delete, ()-> {
            throw new EntityNotFoundException("Movie not found!");
        });

    }

    @Override
    public ShowTime updateShowTime(CreateShowTimeRequest request, Long showId) {
        ShowTime showTime = showTimeRespository.findById(showId)
                .orElseThrow(() -> new ResourceNotFoundException("Show TIME not found"));


        showTime.setTime(request.getTime());
        showTime.setNumberOfSeats(request.getNumberOfSeats());

        return showTimeRespository.save(showTime);
    }

    @Override
    public List<ShowTime> getShowTimesByMovieId(Long movieId) {
      Movie movie = movieRepository.findById(movieId)
              .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
      // Obtenemos los showtimes desde la  entidad directamente
        List<ShowTime> showTimes = movie.getShowTimes();

        if (showTimes.isEmpty()) {
             throw new ResourceNotFoundException("Show time not found for" + movie.getTitle() );
        }
        return showTimes;
    }

    @Override
    public List<ShowTime> getShowTimesByTitle(String title) {
       List<ShowTime> showTimes = showTimeRespository.findByMovieTitleContainingIgnoreCase(title);
        if (showTimes.isEmpty()) {
            throw new ResourceNotFoundException("There is not show for  " + title);
        }

        return showTimes;
    }

    @Override
    public ShowTime getShowTimeById(Long id) {
        return showTimeRespository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Show not found"));
    }

    @Override
    public ShowTimeDto convertShowTimeToDto(ShowTime showTime) {
        ShowTimeDto   dto = new ShowTimeDto();
        dto.setTime(showTime.getTime());
        dto.setNumberOfSeats(showTime.getNumberOfSeats());
        dto.setMovieTitle(showTime.getMovie().getTitle());
        return dto;
    }

    @Override
    public List<ShowTimeDto> getConvertedShowTimesToDto(List<ShowTime> showTimes) {
        return showTimes.stream()
                .map(this::convertShowTimeToDto)
                .collect(Collectors.toList());
    }
}
