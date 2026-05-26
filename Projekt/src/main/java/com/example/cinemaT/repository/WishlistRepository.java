package com.example.cinemaT.repository;

import com.example.cinemaT.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    List<Wishlist> findByOwnerNameOrderByAddedAtDesc(String ownerName);
    Optional<Wishlist> findByOwnerNameAndMovieId(String ownerName, Long movieId);
    void deleteByOwnerNameAndMovieId(String ownerName, Long movieId);
}
