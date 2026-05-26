package com.example.cinemaT.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "review")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    private String authorName;
    private Integer stars;
    @Column(length = 1000)
    private String text;
    private LocalDateTime createdAt;

    public Review(Movie movie, String authorName, Integer stars, String text) {
        this.movie = movie;
        this.authorName = authorName;
        this.stars = stars;
        this.text = text;
        this.createdAt = LocalDateTime.now();
    }
}