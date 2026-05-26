package com.example.cinemaT.repository;

import com.example.cinemaT.model.Movie;
import com.example.cinemaT.model.Movie.MovieStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test repozytorium MovieRepository.
 * @DataJpaTest = uruchamia tylko warstwe JPA (z H2 in-memory) - bez kontrolerow.
 */
@DataJpaTest
@AutoConfigureTestDatabase  // gwarantuje uzycie H2, ignoruje produkcyjny postgres
class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @Test
    void shouldSaveAndFindMovieById() {
        // given
        Movie movie = new Movie();
        movie.setTitle("Testowy film");
        movie.setDirector("Jan Kowalski");
        movie.setGenre("Komedia");
        movie.setRating(7.5);
        movie.setStatus(MovieStatus.NOW_PLAYING);

        // when
        Movie saved = movieRepository.save(movie);
        Optional<Movie> found = movieRepository.findById(saved.getId());

        // then
        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo("Testowy film");
        assertThat(found.get().getDirector()).isEqualTo("Jan Kowalski");
    }

    @Test
    void shouldReturnOnlyNowPlayingMovies() {
        // given - dodajemy 2 filmy w roznych statusach
        Movie m1 = new Movie();
        m1.setTitle("W kinie");
        m1.setStatus(MovieStatus.NOW_PLAYING);
        movieRepository.save(m1);

        Movie m2 = new Movie();
        m2.setTitle("Premiera");
        m2.setStatus(MovieStatus.COMING_SOON);
        movieRepository.save(m2);

        // when
        List<Movie> nowPlaying = movieRepository.findByStatus(MovieStatus.NOW_PLAYING);
        List<Movie> comingSoon = movieRepository.findByStatus(MovieStatus.COMING_SOON);

        // then - sprawdzamy ze filtrowanie dziala
        assertThat(nowPlaying).extracting(Movie::getTitle).contains("W kinie");
        assertThat(nowPlaying).extracting(Movie::getTitle).doesNotContain("Premiera");
        assertThat(comingSoon).extracting(Movie::getTitle).contains("Premiera");
    }

    @Test
    void shouldDeleteMovie() {
        // given
        Movie movie = new Movie();
        movie.setTitle("Do usuniecia");
        movie.setStatus(MovieStatus.NOW_PLAYING);
        Movie saved = movieRepository.save(movie);
        Long id = saved.getId();

        // when
        movieRepository.deleteById(id);

        // then
        assertThat(movieRepository.findById(id)).isEmpty();
    }

    @Test
    void shouldLoadDataFromImportSql() {
        // sprawdzamy ze data.sql zaladowal nasze filmy
        long count = movieRepository.count();
        assertThat(count).isGreaterThanOrEqualTo(6); // 6 filmow w data.sql
    }
}