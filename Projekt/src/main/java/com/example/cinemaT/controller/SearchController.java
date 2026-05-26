package com.example.cinemaT.controller;

import com.example.cinemaT.model.Movie;
import com.example.cinemaT.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Locale;

@Controller
public class SearchController {

    @Autowired private MovieRepository movieRepository;

    @GetMapping("/search")
    public String search(@RequestParam(value = "q", required = false) String q, Model model) {
        String query = q == null ? "" : q.trim();
        List<Movie> results;
        if (query.isEmpty()) {
            results = movieRepository.findAll();
        } else {
            String needle = query.toLowerCase(Locale.ROOT);
            results = movieRepository.findAll().stream()
                    .filter(m -> contains(m.getTitle(), needle)
                            || contains(m.getGenre(), needle)
                            || contains(m.getDirector(), needle)
                            || contains(m.getDescription(), needle))
                    .toList();
        }
        model.addAttribute("query", query);
        model.addAttribute("results", results);
        return "search";
    }

    private boolean contains(String src, String needle) {
        return src != null && src.toLowerCase(Locale.ROOT).contains(needle);
    }
}
