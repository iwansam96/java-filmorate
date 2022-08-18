package ru.yandex.practicum.filmorate.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.controller.adapter.LocalDateAdapter;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    @BeforeEach
    void initClient() {
        System.out.println("web client");
    }

    @BeforeEach
    void initApp() {
        System.out.println("run app");
    }

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturn200() throws Exception {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(LocalDate.class, new LocalDateAdapter());
        Gson gson = builder.create();

        User film = new User("user@test.com", "login", "name", LocalDate.of(1996, 12, 4));
        String requestJson =  gson.toJson(film);

        this.mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void shouldReturn400WhenEmailIsNotValid() throws Exception {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(LocalDate.class, new LocalDateAdapter());
        Gson gson = builder.create();

        User film = new User("usertest.com", "login", "name", LocalDate.of(1996, 12, 4));
        String requestJson =  gson.toJson(film);

        this.mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andDo(print()).andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldReturn400WhenEmailIsBlank() throws Exception {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(LocalDate.class, new LocalDateAdapter());
        Gson gson = builder.create();

        User film = new User("", "login", "name", LocalDate.of(1996, 12, 4));
        String requestJson =  gson.toJson(film);

        this.mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andDo(print()).andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldReturn400WhenLoginIsNotValid() throws Exception {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(LocalDate.class, new LocalDateAdapter());
        Gson gson = builder.create();

        User film = new User("user@test.com", "log in", "name", LocalDate.of(1996, 12, 4));
        String requestJson =  gson.toJson(film);

        this.mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andDo(print()).andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldReturn400WhenLoginIsBlank() throws Exception {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(LocalDate.class, new LocalDateAdapter());
        Gson gson = builder.create();

        User film = new User("user@test.com", "", "name", LocalDate.of(1996, 12, 4));
        String requestJson =  gson.toJson(film);

        this.mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andDo(print()).andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldReturn400WhenBirthdayIsNotValid() throws Exception {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(LocalDate.class, new LocalDateAdapter());
        Gson gson = builder.create();

        User film = new User("user@test.com", "login", "name", LocalDate.now().plusDays(1));
        String requestJson =  gson.toJson(film);

        this.mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andDo(print()).andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldReturn200WhenNameIsBlank() throws Exception {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(LocalDate.class, new LocalDateAdapter());
        Gson gson = builder.create();

        User film = new User("user@test.com", "login", "", LocalDate.of(1996, 12, 4));
        String requestJson =  gson.toJson(film);

        User film2 = new User("user@test.com", "login", "login", LocalDate.of(1996, 12, 4));
        film2.setId(film.getId());
        String film2json =  gson.toJson(film2);

        this.mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().json(film2json));
    }

}
