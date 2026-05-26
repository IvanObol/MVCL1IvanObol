package com.example.cinemaT.repository;

import com.example.cinemaT.model.Movie;
import com.example.cinemaT.model.Movie.MovieStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByStatus(MovieStatus status);
}