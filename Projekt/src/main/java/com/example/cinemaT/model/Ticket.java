package com.example.cinemaT.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "ticket")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ownerName;
    
    private String email;

    private String paymentMethod;


    private String purchaseGroupId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "showing_id")
    private Showing showing;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id")
    private Seat seat;

    private LocalDateTime purchasedAt;
}