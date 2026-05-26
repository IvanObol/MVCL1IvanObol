package com.example.cinemaT.controller;

import com.example.cinemaT.model.Movie;
import com.example.cinemaT.model.Wishlist;
import com.example.cinemaT.repository.MovieRepository;
import com.example.cinemaT.repository.WishlistRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WishlistController {

    @Autowired private MovieRepository movieRepository;
    @Autowired private WishlistRepository wishlistRepository;

    @PostMapping("/movie/{movieId}/wishlist/add")
    public String add(@PathVariable Long movieId, HttpSession session) {
        String user = (String) session.getAttribute("currentUser");
        if (user == null) return "redirect:/login";

        if (wishlistRepository.findByOwnerNameAndMovieId(user, movieId).isEmpty()) {
            Movie m = movieRepository.findById(movieId).orElse(null);
            if (m != null) wishlistRepository.save(new Wishlist(user, m));
        }
        return "redirect:/movie/" + movieId + "?added=1";
    }

    @PostMapping("/movie/{movieId}/wishlist/remove")
    @Transactional
    public String remove(@PathVariable Long movieId, HttpSession session) {
        String user = (String) session.getAttribute("currentUser");
        if (user == null) return "redirect:/login";

        wishlistRepository.deleteByOwnerNameAndMovieId(user, movieId);
        return "redirect:/moje-kino";
    }
}
