package com.example.cinemaT.controller;

import com.example.cinemaT.model.Movie;
import com.example.cinemaT.model.Movie.MovieStatus;
import com.example.cinemaT.model.Review;
import com.example.cinemaT.model.Ticket;
import com.example.cinemaT.model.Wishlist;
import com.example.cinemaT.repository.MovieRepository;
import com.example.cinemaT.repository.ReviewRepository;
import com.example.cinemaT.repository.TicketRepository;
import com.example.cinemaT.repository.WishlistRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    @Autowired private MovieRepository movieRepository;
    @Autowired private TicketRepository ticketRepository;
    @Autowired private ReviewRepository reviewRepository;
    @Autowired private WishlistRepository wishlistRepository;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("nowPlaying", movieRepository.findByStatus(MovieStatus.NOW_PLAYING));
        model.addAttribute("comingSoon", movieRepository.findByStatus(MovieStatus.COMING_SOON));
        return "index";
    }

    @GetMapping("/pomoc")
    public String pomoc() {
        return "pomoc";
    }

    @GetMapping("/moje-kino")
    public String mojeKino(HttpSession session, Model model) {
        String user = (String) session.getAttribute("currentUser");
        if (user == null) {
            return "redirect:/login";
        }

        List<Ticket> tickets = ticketRepository.findByOwnerNameOrderByPurchasedAtDesc(user);
        Map<String, List<Ticket>> grouped = tickets.stream()
                .collect(Collectors.groupingBy(
                        Ticket::getPurchaseGroupId,
                        LinkedHashMap::new,
                        Collectors.toList()));

        model.addAttribute("groupedTickets", grouped);
        model.addAttribute("ticketCount", tickets.size());

        List<Movie> watched = tickets.stream()
                .map(t -> t.getShowing().getMovie())
                .distinct()
                .limit(6)
                .toList();
        model.addAttribute("watchedMovies", watched);

        // Chce obejrzec
        List<Wishlist> wishlist = wishlistRepository.findByOwnerNameOrderByAddedAtDesc(user);
        model.addAttribute("wishlist", wishlist);

        // Moje oceny oraz komentarze
        List<Review> myReviews = reviewRepository.findByAuthorNameOrderByCreatedAtDesc(user);
        model.addAttribute("myReviews", myReviews);

        // Pelna biblioteka
        model.addAttribute("allMovies", movieRepository.findAll());
        return "moje-kino";
    }
}
