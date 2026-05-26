package com.example.cinemaT.repository;

import com.example.cinemaT.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByMovieIdOrderByCreatedAtDesc(Long movieId);
    List<Review> findByAuthorNameOrderByCreatedAtDesc(String authorName);
}
