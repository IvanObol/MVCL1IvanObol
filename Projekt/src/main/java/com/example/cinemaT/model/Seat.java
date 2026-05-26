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
    private boolean reserved;

    @ManyToOne
    @JoinColumn(name = "showing_id")
    private Showing showing;

    public Seat(int rowNum, int seatNum, boolean reserved, Showing showing) {
        this.rowNum = rowNum;
        this.seatNum = seatNum;
        this.reserved = reserved;
        this.showing = showing;
    }
}