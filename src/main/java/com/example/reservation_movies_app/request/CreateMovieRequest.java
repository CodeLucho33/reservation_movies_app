package com.example.reservation_movies_app.request;

import com.example.reservation_movies_app.enums.GenreMovie;
import com.example.reservation_movies_app.model.ShowTime;
import lombok.Data;

import java.util.List;

@Data
public class CreateMovieRequest {
    private String title;
    private String description;
    private int year;
    private String posterPath;
    private GenreMovie genreMovie;
   // private List<ShowTime> showTimes;
}
