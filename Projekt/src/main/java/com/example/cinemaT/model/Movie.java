package com.example.cinemaT.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

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
    @Column(length = 1000)
    private String description;
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<Seat> seats;
}
