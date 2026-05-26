package com.example.cinemaT.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "showing")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Showing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

    private LocalDateTime startTime;
    private Integer hallNumber;

    @OneToMany(mappedBy = "showing", cascade = CascadeType.ALL)
    private List<Seat> seats = new ArrayList<>();

    public Showing(Movie movie, Cinema cinema, LocalDateTime startTime, Integer hallNumber) {
        this.movie = movie;
        this.cinema = cinema;
        this.startTime = startTime;
        this.hallNumber = hallNumber;
    }
}