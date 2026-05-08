package com.example.cinemaT.controller;

import com.example.cinemaT.model.Movie;
import com.example.cinemaT.model.Seat;
import com.example.cinemaT.repository.MovieRepository;
import com.example.cinemaT.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("/")
    public String showMainPage(Model model) {
        model.addAttribute("movies", movieRepository.findAll());
        return "index";
    }
    @GetMapping("/movie/{id}")
    public String getMovieDetails(@PathVariable Long id, Model model) {
        return movieRepository.findById(id)
                .map(movie -> {
                    model.addAttribute("movie", movie);
                    return "details";
                })
                .orElse("redirect:/");
    }
    @Autowired
    private SeatRepository seatRepository;

    @PostMapping("/movie/{movieId}/reserve/{seatId}")
    public String reserveSeat(@PathVariable Long movieId, @PathVariable Long seatId) {
        Seat seat = seatRepository.findById(seatId).orElseThrow();
        seat.setReserved(true);
        seatRepository.save(seat);
        return "redirect:/movie/" + movieId + "/hall";
    }
    @GetMapping("/movie/{id}/hall")
    public String showMovieHall(@PathVariable Long id, Model model) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid movie Id:" + id));

        model.addAttribute("movie", movie);
        return "hall";
    }

}