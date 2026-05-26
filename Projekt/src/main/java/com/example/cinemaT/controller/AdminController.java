package com.example.cinemaT.controller;

import com.example.cinemaT.component.DataInitializer;
import com.example.cinemaT.model.Movie;
import com.example.cinemaT.model.Movie.MovieStatus;
import com.example.cinemaT.repository.MovieRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired private MovieRepository movieRepository;
    @Autowired private DataInitializer dataInitializer;

    private boolean isNotAdmin(HttpSession session) {
        return !"ADMIN".equals(session.getAttribute("currentRole"));
    }

    @GetMapping
    public String adminPanel(HttpSession session, Model model) {
        if (isNotAdmin(session)) return "redirect:/login";
        model.addAttribute("movies", movieRepository.findAll());
        return "admin";
    }

    @GetMapping("/movie/new")
    public String newMovieForm(HttpSession session, Model model) {
        if (isNotAdmin(session)) return "redirect:/login";
        model.addAttribute("movie", new Movie());
        return "admin-movie-form";
    }

    @PostMapping("/movie/save")
    public String saveMovie(HttpSession session, @ModelAttribute Movie movie) {
        if (isNotAdmin(session)) return "redirect:/login";
        if (movie.getStatus() == null) movie.setStatus(MovieStatus.NOW_PLAYING);
        movieRepository.save(movie);

        dataInitializer.run();

        return "redirect:/admin";
    }

    @PostMapping("/movie/{id}/delete")
    public String deleteMovie(HttpSession session, @PathVariable Long id) {
        if (isNotAdmin(session)) return "redirect:/login";
        movieRepository.deleteById(id);
        return "redirect:/admin";
    }
}