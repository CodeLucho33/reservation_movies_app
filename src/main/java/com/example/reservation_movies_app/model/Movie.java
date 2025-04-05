package com.example.reservation_movies_app.model;

import com.example.reservation_movies_app.enums.GenreMovie;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private int year;
    private String posterPath;
    @Enumerated(EnumType.STRING)
    private GenreMovie genreMovie;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true  )
    private List<ShowTime> showTimes;

}
