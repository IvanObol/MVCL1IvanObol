package com.example.cinemaT.controller;

import com.example.cinemaT.model.Movie;
import com.example.cinemaT.repository.MovieRepository;
import com.example.cinemaT.repository.ReviewRepository;
import com.example.cinemaT.repository.WishlistRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MovieController {

    @Autowired private MovieRepository movieRepository;
    @Autowired private ReviewRepository reviewRepository;
    @Autowired private WishlistRepository wishlistRepository;

    @GetMapping("/movie/{id}")
    public String movieDetails(@PathVariable Long id, HttpSession session, Model model) {
        Movie movie = movieRepository.findById(id).orElse(null);
        if (movie == null) return "redirect:/";

        model.addAttribute("movie", movie);
        model.addAttribute("reviews", reviewRepository.findByMovieIdOrderByCreatedAtDesc(id));

        String user = (String) session.getAttribute("currentUser");
        boolean inWishlist = user != null
                && wishlistRepository.findByOwnerNameAndMovieId(user, id).isPresent();
        model.addAttribute("inWishlist", inWishlist);
        return "movie-details";
    }
}
