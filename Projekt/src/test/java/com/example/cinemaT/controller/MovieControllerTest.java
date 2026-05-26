package com.example.cinemaT.controller;

import com.example.cinemaT.model.Movie;
import com.example.cinemaT.model.Movie.MovieStatus;
import com.example.cinemaT.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test kontrolera HomeController.
 * @WebMvcTest = uruchamia tylko warstwe MVC, bez prawdziwej bazy.
 * MockBean podstawia "zamulone" repozytorium zamiast prawdziwego.
 */
@WebMvcTest(HomeController.class)
class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieRepository movieRepository;

    private Movie diuna;
    private Movie chronologia;

    @BeforeEach
    void setUp() {
        diuna = new Movie();
        diuna.setId(1L);
        diuna.setTitle("Diuna");
        diuna.setStatus(MovieStatus.NOW_PLAYING);

        chronologia = new Movie();
        chronologia.setId(2L);
        chronologia.setTitle("Chronologia wody");
        chronologia.setStatus(MovieStatus.COMING_SOON);
    }

    @Test
    void homePageShouldReturnIndexWithMovies() throws Exception {
        // given
        when(movieRepository.findByStatus(MovieStatus.NOW_PLAYING))
                .thenReturn(List.of(diuna));
        when(movieRepository.findByStatus(MovieStatus.COMING_SOON))
                .thenReturn(List.of(chronologia));

        // when + then
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())                       // 200 OK
                .andExpect(view().name("index"))                  // szablon = index.html
                .andExpect(model().attributeExists("nowPlaying")) // model zawiera filmy
                .andExpect(model().attributeExists("comingSoon"))
                .andExpect(model().attribute("nowPlaying", hasSize(1)))
                .andExpect(model().attribute("comingSoon", hasSize(1)));
    }

    @Test
    void pomocPageShouldReturnPomocView() throws Exception {
        mockMvc.perform(get("/pomoc"))
                .andExpect(status().isOk())
                .andExpect(view().name("pomoc"));
    }

    @Test
    void mojeKinoShouldRedirectWhenNotLoggedIn() throws Exception {
        // bez sesji uzytkownika - redirect na /login
        mockMvc.perform(get("/moje-kino"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    void mojeKinoShouldShowPageWhenLoggedIn() throws Exception {
        when(movieRepository.findAll()).thenReturn(List.of(diuna));

        // symulujemy zalogowanego uzytkownika przez sessionAttr
        mockMvc.perform(get("/moje-kino").sessionAttr("currentUser", "Ivan"))
                .andExpect(status().isOk())
                .andExpect(view().name("moje-kino"))
                .andExpect(model().attributeExists("watchedMovies"));
    }
}