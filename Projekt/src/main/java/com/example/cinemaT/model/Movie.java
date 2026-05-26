package com.example.cinemaT.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movie")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String director;
    private Double rating;
    private String genre;
    private String posterUrl;

    @Column(length = 2000)
    private String description;

    private Integer duration;
    private String ageLimit;
    private String country;
    private Integer releaseYear;
    private String language;
    private String format;

    @Enumerated(EnumType.STRING)
    private MovieStatus status = MovieStatus.NOW_PLAYING;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    public enum MovieStatus { NOW_PLAYING, COMING_SOON }
}