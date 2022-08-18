package ru.yandex.practicum.filmorate.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.controller.adapter.LocalDateAdapter;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FilmController.class)
public class FilmControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturn200() throws Exception {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(LocalDate.class, new LocalDateAdapter());
        Gson gson = builder.create();

        Film film = new Film("name", "desc", LocalDate.of(2021, 10, 22), 200);
        String requestJson =  gson.toJson(film);

        this.mockMvc.perform(post("/films").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturn400WhenNameIsBlank() throws Exception {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(LocalDate.class, new LocalDateAdapter());
        Gson gson = builder.create();

        Film film = new Film("", "desc", LocalDate.of(2021, 10, 22), 200);
        String requestJson =  gson.toJson(film);

        this.mockMvc.perform(post("/films").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldReturn400WhenDescriptionIsLong() throws Exception {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(LocalDate.class, new LocalDateAdapter());
        Gson gson = builder.create();

        Film film = new Film("name",
                "asdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasd" +
                        "fasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfas" +
                        "dfasdfasdfasdfasdfasdfasdfa", LocalDate.of(2021, 10, 22), 200);
        String requestJson =  gson.toJson(film);

        this.mockMvc.perform(post("/films").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldReturn400WhenReleaseDateIsNotValid() throws Exception {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(LocalDate.class, new LocalDateAdapter());
        Gson gson = builder.create();

        Film film = new Film("name", "desc", LocalDate.of(1895, 12, 27), 200);
        String requestJson =  gson.toJson(film);

        this.mockMvc.perform(post("/films").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldReturn400WhenDurationIsNegative() throws Exception {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(LocalDate.class, new LocalDateAdapter());
        Gson gson = builder.create();

        Film film = new Film("name", "desc", LocalDate.of(2021, 10, 22), -200);
        String requestJson =  gson.toJson(film);

        this.mockMvc.perform(post("/films").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().is4xxClientError());
    }
}
