package com.example.cinemaT.repository;

import com.example.cinemaT.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByOwnerNameOrderByPurchasedAtDesc(String ownerName);
    List<Ticket> findByEmailOrderByPurchasedAtDesc(String email);
}