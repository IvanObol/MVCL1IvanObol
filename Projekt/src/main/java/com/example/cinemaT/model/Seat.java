package com.example.cinemaT.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rowNum;
    private int seatNum;
    private boolean isReserved;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    public Seat(int rowNum, int seatNum, boolean isReserved, Movie movie) {
        this.rowNum = rowNum;
        this.seatNum = seatNum;
        this.isReserved = isReserved;
        this.movie = movie;
    }
}