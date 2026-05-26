package com.example.cinemaT.controller;

import com.example.cinemaT.model.Movie;
import com.example.cinemaT.model.Review;
import com.example.cinemaT.repository.MovieRepository;
import com.example.cinemaT.repository.ReviewRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReviewController {

    @Autowired private MovieRepository movieRepository;
    @Autowired private ReviewRepository reviewRepository;

    @PostMapping("/movie/{movieId}/comment")
    public String addComment(@PathVariable Long movieId,
                             @RequestParam Integer stars,
                             @RequestParam String text,
                             HttpSession session) {

        String author = (String) session.getAttribute("currentUser");
        if (author == null) author = "Anonim";

        Movie movie = movieRepository.findById(movieId).orElseThrow();
        Review review = new Review(movie, author, stars, text);
        reviewRepository.save(review);

        return "redirect:/movie/" + movieId;
    }
}