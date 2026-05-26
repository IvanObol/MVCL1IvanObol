package com.example.cinemaT.controller;

import com.example.cinemaT.model.Movie;
import com.example.cinemaT.model.Showing;
import com.example.cinemaT.repository.MovieRepository;
import com.example.cinemaT.repository.ShowingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class ZaplanujController {

    @Autowired private ShowingRepository showingRepository;
    @Autowired private MovieRepository movieRepository;

    @GetMapping("/zaplanuj")
    public String zaplanuj(@RequestParam(value = "date", required = false) String dateStr,
                           Model model) {
        LocalDate selected = parseOrToday(dateStr);

        // wszystkie seansy danego dnia
        List<Showing> all = showingRepository.findAll();
        List<Showing> dayShowings = all.stream()
                .filter(s -> s.getStartTime() != null
                        && s.getStartTime().toLocalDate().equals(selected))
                .sorted((a, b) -> a.getStartTime().compareTo(b.getStartTime()))
                .toList();

        // pogrupowane po filmie
        Map<Movie, List<Showing>> grouped = dayShowings.stream()
                .collect(Collectors.groupingBy(Showing::getMovie,
                        java.util.LinkedHashMap::new,
                        Collectors.toList()));

        // wszystkie daty z seansami
        List<String> activeDates = all.stream()
                .map(s -> s.getStartTime().toLocalDate().toString())
                .distinct()
                .sorted()
                .toList();

        model.addAttribute("selectedDate", selected.toString());
        model.addAttribute("selectedDateLabel", formatPl(selected));
        model.addAttribute("groupedShowings", grouped);
        model.addAttribute("activeDates", activeDates);
        model.addAttribute("totalMovies", movieRepository.count());
        return "zaplanuj";
    }

    private LocalDate parseOrToday(String dateStr) {
        if (dateStr == null || dateStr.isBlank()) {
            return LocalDate.of(2026, 5, 24);
        }
        try {
            return LocalDate.parse(dateStr);
        } catch (Exception e) {
            return LocalDate.of(2026, 5, 24);
        }
    }

    private String formatPl(LocalDate d) {
        String[] months = {
                "stycznia","lutego","marca","kwietnia","maja","czerwca",
                "lipca","sierpnia","września","października","listopada","grudnia"
        };
        return d.getDayOfMonth() + " " + months[d.getMonthValue()-1] + " " + d.getYear();
    }
}
