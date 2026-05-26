package com.example.cinemaT.component;

import com.example.cinemaT.model.*;
import com.example.cinemaT.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Component
public class DataInitializer implements CommandLineRunner {

    private final MovieRepository movieRepository;
    private final CinemaRepository cinemaRepository;
    private final ShowingRepository showingRepository;
    private final SeatRepository seatRepository;
    private final ReviewRepository reviewRepository;

    public DataInitializer(MovieRepository movieRepository,
                           CinemaRepository cinemaRepository,
                           ShowingRepository showingRepository,
                           SeatRepository seatRepository,
                           ReviewRepository reviewRepository) {
        this.movieRepository = movieRepository;
        this.cinemaRepository = cinemaRepository;
        this.showingRepository = showingRepository;
        this.seatRepository = seatRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public void run(String... args) {
        System.out.println("=== DataInitializer START ===");

        List<Movie> movies = movieRepository.findAll();
        List<Cinema> cinemas = cinemaRepository.findAll();

        if (movies.isEmpty() || cinemas.isEmpty()) {
            System.err.println("BRAK FILMOW LUB KIN! Sprawdz data.sql");
            return;
        }


        Random random = new Random(42);
        int[][] timeSlots = { {17, 45}, {20, 30}, {18, 0}, {21, 15}, {19, 0}, {22, 0}, {16, 0} };

        int createdCount = 0;
        for (Movie movie : movies) {

            if (!showingRepository.findByMovieId(movie.getId()).isEmpty()) continue;

            for (int i = 0; i < 3; i++) {
                Cinema cinema = cinemas.get(random.nextInt(cinemas.size()));
                int[] slot = timeSlots[random.nextInt(timeSlots.length)];
                int dayOffset = random.nextInt(2);
                LocalDateTime time = LocalDateTime.of(2026, 5, 24 + dayOffset, slot[0], slot[1]);
                int hall = random.nextInt(4) + 1;

                createShowing(movie, cinema, time, hall);
                createdCount++;
            }
        }
        System.out.println("Utworzono nowych seansow: " + createdCount);
        System.out.println("Lacznie seansow w bazie: " + showingRepository.count());
        System.out.println("Lacznie miejsc w bazie: " + seatRepository.count());


        if (reviewRepository.count() == 0) {
            Movie maryja = movies.stream().filter(m -> "Maryja. Matka papieża".equals(m.getTitle()))
                    .findFirst().orElse(null);
            Movie diuna  = movies.stream().filter(m -> m.getTitle().startsWith("Diuna"))
                    .findFirst().orElse(null);

            if (maryja != null) {
                reviewRepository.save(new Review(maryja, "Mamut Rahal", 5,
                        "Bardzo spokojny i poruszający film. Nie ma tu pośpiechu ani przesadnego dramatyzmu - bardziej refleksja nad tym, jak ważne są dom, wiara i pamięć o bliskich."));
                reviewRepository.save(new Review(maryja, "Jasosu Bibu", 5,
                        "Najbardziej doceniłem archiwalne materiały i ton narracji. Film jest prosty, ale szczery."));
                reviewRepository.save(new Review(maryja, "Vitalii Tretiakov", 5,
                        "Piękna opowieść o matce, której obecność wybrzmiewa przez wspomnienia i wartości."));
                reviewRepository.save(new Review(maryja, "Jefrey Epstein", 4,
                        "Film może być zbyt spokojny dla osób szukających dynamicznej historii, ale warto zobaczyć."));
            }
            if (diuna != null) {
                reviewRepository.save(new Review(diuna, "Ivan Obol", 5,
                        "Świetne tempo, ogromna skala i muzyka, która zostaje w głowie jeszcze długo po seansie."));
                reviewRepository.save(new Review(diuna, "Anna K.", 5,
                        "Najlepsza kontynuacja jaką widziałam. Wizualnie majestat."));
            }
            System.out.println("Utworzono komentarzy: " + reviewRepository.count());
        }

        System.out.println("=== Inicjalizacja danych zakonczona ✅ ===");
    }


    private void createShowing(Movie movie, Cinema cinema, LocalDateTime time, int hall) {
        Showing showing = showingRepository.save(new Showing(movie, cinema, time, hall));
        for (int r = 1; r <= 8; r++) {
            for (int s = 1; s <= 8; s++) {
                boolean reserved = (r == 3 && s == 4) || (r == 5 && s == 7);
                seatRepository.save(new Seat(r, s, reserved, showing));
            }
        }
    }
}