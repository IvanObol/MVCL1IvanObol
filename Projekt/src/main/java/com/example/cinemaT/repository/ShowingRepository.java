package com.example.cinemaT.repository;

import com.example.cinemaT.model.Showing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShowingRepository extends JpaRepository<Showing, Long> {
    List<Showing> findByMovieId(Long movieId);
    List<Showing> findByMovieIdAndCinemaId(Long movieId, Long cinemaId);
}