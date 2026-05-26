package com.example.cinemaT.controller;

import com.example.cinemaT.model.*;
import com.example.cinemaT.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;
import java.util.UUID;

@Controller
@RequestMapping("/bilety")
public class BookingController {


    private static final int TICKET_PRICE_PLN = 28;

    @Autowired private MovieRepository movieRepository;
    @Autowired private CinemaRepository cinemaRepository;
    @Autowired private ShowingRepository showingRepository;
    @Autowired private SeatRepository seatRepository;
    @Autowired private TicketRepository ticketRepository;

    // Wybor filmu

    @GetMapping("/{movieId}")
    public String step1(@PathVariable Long movieId, Model model) {
        Movie movie = movieRepository.findById(movieId).orElse(null);
        if (movie == null) return "redirect:/";

        List<Map<String, Object>> showingsForJs = showingRepository.findByMovieId(movieId).stream()
                .map(s -> {
                    Map<String, Object> cinemaMap = new LinkedHashMap<>();
                    cinemaMap.put("id", s.getCinema().getId());
                    cinemaMap.put("name", s.getCinema().getName());

                    Map<String, Object> showingMap = new LinkedHashMap<>();
                    showingMap.put("id", s.getId());
                    showingMap.put("cinema", cinemaMap);
                    showingMap.put("startTime", s.getStartTime().toString());
                    showingMap.put("hallNumber", s.getHallNumber());
                    return showingMap;
                })
                .toList();

        model.addAttribute("movie", movie);
        model.addAttribute("cinemas", cinemaRepository.findAll());
        model.addAttribute("showings", showingsForJs);
        return "booking-step1";
    }

    @PostMapping("/{movieId}/wybierz")
    public String chooseShowing(@PathVariable Long movieId,
                                @RequestParam Long showingId,
                                HttpSession session) {
        session.setAttribute("showingId", showingId);
        // przy nowym seansie kasujemy poprzedni wybor miejsc
        session.removeAttribute("seatIds");
        return "redirect:/bilety/seats";
    }

    // Wybor miejsc

    @GetMapping("/seats")
    public String step2(HttpSession session, Model model) {
        Long showingId = (Long) session.getAttribute("showingId");
        if (showingId == null) return "redirect:/";

        Showing showing = showingRepository.findById(showingId).orElseThrow();
        List<Seat> seats = seatRepository.findByShowingIdOrderByRowNumAscSeatNumAsc(showingId);

        model.addAttribute("showing", showing);
        model.addAttribute("seats", seats);
        model.addAttribute("ticketPrice", TICKET_PRICE_PLN);
        return "booking-step2";
    }

    @PostMapping("/seats/reserve")
    public String chooseSeats(@RequestParam(name = "seatIds", required = false) List<Long> seatIds,
                              HttpSession session) {
        if (seatIds == null || seatIds.isEmpty()) {
            return "redirect:/bilety/seats";
        }
        session.setAttribute("seatIds", new ArrayList<>(seatIds));
        return "redirect:/bilety/payment";
    }

    // Dane oraz platnoszcz

    @GetMapping("/payment")
    public String step3(HttpSession session, Model model) {
        Long showingId = (Long) session.getAttribute("showingId");
        List<Seat> seats = loadSelectedSeats(session);
        if (showingId == null || seats.isEmpty()) return "redirect:/";

        model.addAttribute("showing", showingRepository.findById(showingId).orElseThrow());
        model.addAttribute("seats", seats);
        model.addAttribute("ticketPrice", TICKET_PRICE_PLN);
        model.addAttribute("totalPrice", seats.size() * TICKET_PRICE_PLN);
        return "booking-step3";
    }

    @PostMapping("/payment")
    public String saveCustomerData(@RequestParam String firstName,
                                   @RequestParam String lastName,
                                   @RequestParam String email,
                                   @RequestParam String phone,
                                   @RequestParam String paymentMethod,
                                   HttpSession session) {
        session.setAttribute("firstName", firstName);
        session.setAttribute("lastName", lastName);
        session.setAttribute("email", email);
        session.setAttribute("phone", phone);
        session.setAttribute("paymentMethod", paymentMethod);
        return "redirect:/bilety/summary";
    }

    // Podsumowanie

    @GetMapping("/summary")
    public String step4(HttpSession session, Model model) {
        Long showingId = (Long) session.getAttribute("showingId");
        List<Seat> seats = loadSelectedSeats(session);
        if (showingId == null || seats.isEmpty()) return "redirect:/";

        model.addAttribute("showing",       showingRepository.findById(showingId).orElseThrow());
        model.addAttribute("seats",         seats);
        model.addAttribute("ticketPrice",   TICKET_PRICE_PLN);
        model.addAttribute("totalPrice",    seats.size() * TICKET_PRICE_PLN);
        model.addAttribute("firstName",     session.getAttribute("firstName"));
        model.addAttribute("lastName",      session.getAttribute("lastName"));
        model.addAttribute("email",         session.getAttribute("email"));
        model.addAttribute("phone",         session.getAttribute("phone"));
        model.addAttribute("paymentMethod", session.getAttribute("paymentMethod"));
        return "booking-step4";
    }

    // Potwirdzenie

    @PostMapping("/confirm")
    public String confirm(HttpSession session) {
        List<Seat> seats = loadSelectedSeats(session);
        if (seats.isEmpty()) return "redirect:/";

        Long showingId = (Long) session.getAttribute("showingId");
        Showing showing = showingRepository.findById(showingId).orElseThrow();

        String ownerName     = (String) session.getAttribute("currentUser");
        String email         = (String) session.getAttribute("email");
        String paymentMethod = (String) session.getAttribute("paymentMethod");
        String groupId       = UUID.randomUUID().toString();
        LocalDateTime now    = LocalDateTime.now();

        for (Seat seat : seats) {
            seat.setReserved(true);
            seatRepository.save(seat);

            Ticket ticket = new Ticket();
            ticket.setOwnerName(ownerName);
            ticket.setEmail(email);
            ticket.setPaymentMethod(paymentMethod);
            ticket.setPurchaseGroupId(groupId);
            ticket.setShowing(showing);
            ticket.setSeat(seat);
            ticket.setPurchasedAt(now);
            ticketRepository.save(ticket);
        }
        return "redirect:/bilety/success";
    }

    @GetMapping("/success")
    public String success(HttpSession session, Model model) {
        List<Seat> seats = loadSelectedSeats(session);
        model.addAttribute("email", session.getAttribute("email"));
        model.addAttribute("ticketCount", seats.size());
        model.addAttribute("totalPrice", seats.size() * TICKET_PRICE_PLN);
        session.removeAttribute("showingId");
        session.removeAttribute("seatIds");
        return "booking-success";
    }

    @SuppressWarnings("unchecked")
    private List<Seat> loadSelectedSeats(HttpSession session) {
        Object raw = session.getAttribute("seatIds");
        if (!(raw instanceof List<?>) || ((List<?>) raw).isEmpty()) return List.of();

        List<Long> ids = (List<Long>) raw;
        List<Seat> seats = new ArrayList<>(seatRepository.findAllById(ids));
        seats.sort(Comparator.comparingInt(Seat::getRowNum).thenComparingInt(Seat::getSeatNum));
        return seats;
    }
}