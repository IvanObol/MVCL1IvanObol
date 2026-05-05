package com.example.cinemaT.controller;

import com.example.cinemaT.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("/")
    public String showMainPage(Model model) {
        model.addAttribute("movies", movieRepository.findAll());
        return "index";
    }
}