package com.example.reservation_movies_app.controller;

import com.example.reservation_movies_app.dto.ShowTimeDto;
import com.example.reservation_movies_app.exception.AlreadyExistsException;
import com.example.reservation_movies_app.exception.ResourceNotFoundException;
import com.example.reservation_movies_app.model.ShowTime;
import com.example.reservation_movies_app.request.CreateShowTimeRequest;
import com.example.reservation_movies_app.response.ApiResponse;
import com.example.reservation_movies_app.service.showtime.IShowTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/show")
public class ShowTimeController {
    private final IShowTimeService showTimeService;



    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> createShowTime(@RequestBody CreateShowTimeRequest request) {
        try {
            ShowTime showTime = showTimeService.createShowTime(request);
            ShowTimeDto showTimeDto = showTimeService.convertShowTimeToDto(showTime);
            return ResponseEntity.ok(new ApiResponse("Success adition showtime ", showTimeDto));
        } catch (AlreadyExistsException e) {
          return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("Show time already exists", null));
        }catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Movie not found", null));
        }

    }

    @DeleteMapping("/{showId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteShowTime(@PathVariable Long showId) {
        try {
            showTimeService.deleteShowTime(showId);
            return ResponseEntity.ok(new ApiResponse("Success delete showtime", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Show time not found", null));
        }

    }


    @PutMapping("/{showId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> updateShowTime(@RequestBody CreateShowTimeRequest request, @PathVariable Long showId) {
        try {
            ShowTime showTime = showTimeService.updateShowTime(request, showId);
            ShowTimeDto showTimeDto = showTimeService.convertShowTimeToDto(showTime);
            return ResponseEntity.ok(new ApiResponse("Success updated showtime ", showTimeDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Show time not found", null));
        }
    }


    @GetMapping("movie-id/{movieId}")
    @PreAuthorize("hasRole('REGULAR')")
    public ResponseEntity<ApiResponse> getShowTimesByMovieId(@PathVariable Long movieId){
        try {
            List<ShowTime> showTimes = showTimeService.getShowTimesByMovieId(movieId);
            List<ShowTimeDto> showTimeDtos = showTimeService.getConvertedShowTimesToDto(showTimes);
            return ResponseEntity.ok(new ApiResponse("Success retrieved show/movies", showTimeDtos));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Show times not found", null));
        }
    }


    @GetMapping(params = "title")
    @PreAuthorize("hasRole('REGULAR')")
    public ResponseEntity<ApiResponse> getShowTimesByTitle(@RequestParam String title){
        try {
            List<ShowTime> showTimes = showTimeService.getShowTimesByTitle(title);
            List<ShowTimeDto> showTimeDtos = showTimeService.getConvertedShowTimesToDto(showTimes);
            return ResponseEntity.ok(new ApiResponse("Success retrieved show/movies", showTimeDtos));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Show times not found", null));
        }

    }

    @GetMapping("/{showId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> getShowTimeById(@PathVariable Long showId){
        try {
            ShowTime showTime = showTimeService.getShowTimeById(showId);
            ShowTimeDto showTimeDto = showTimeService.convertShowTimeToDto(showTime);
            return ResponseEntity.ok(new ApiResponse("Success retrieved show/movies", showTimeDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Show time not found", null));
        }
    }

    @PreAuthorize("hasRole('REGULAR')")
    @GetMapping
    public ResponseEntity<ApiResponse> getAllShowTimes() {
        List<ShowTime> showTimes = showTimeService.getAllShowTimes();
        List<ShowTimeDto> showTimeDto = showTimeService.getConvertedShowTimesToDto(showTimes);
        return ResponseEntity.ok(new ApiResponse("Success Get All Users", showTimeDto));
    }
}
