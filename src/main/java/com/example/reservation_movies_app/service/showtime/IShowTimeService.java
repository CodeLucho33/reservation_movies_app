package com.example.reservation_movies_app.service.showtime;


import com.example.reservation_movies_app.dto.ShowTimeDto;
import com.example.reservation_movies_app.model.ShowTime;
import com.example.reservation_movies_app.request.CreateShowTimeRequest;

import java.util.List;

public interface IShowTimeService {
    ShowTime createShowTime(CreateShowTimeRequest request);
    void deleteShowTime(Long showId);
    ShowTime updateShowTime(CreateShowTimeRequest request, Long showId);
    List<ShowTime> getShowTimesByMovieId(Long movieId);
    List<ShowTime> getShowTimesByTitle(String title);
    ShowTime getShowTimeById(Long id);
    ShowTimeDto convertShowTimeToDto(ShowTime showTime);
    List<ShowTimeDto> getConvertedShowTimesToDto(List<ShowTime> showTimes);

}
