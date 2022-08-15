package ru.yandex.practicum.filmorate.controller.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class FilmControlerTest {
    FilmController controller;

    @BeforeEach
    public void createFilmController() {
        controller = new FilmController();
    }

    @Test
    public void shouldReturnNullWhenNameIsBlank() {
        Film film = Film.builder()
                .id(0)
                .name("")
                .description("asdf")
                .releaseDate(LocalDate.of(1996, 12, 4))
                .duration(Duration.ofHours(2))
                .build();

        Film result = controller.create(film);

        assertNull(result);
    }

    @Test
    public void shouldReturnNullWhenDescriptionIsLong() {
        Film film = Film.builder()
                .id(0)
                .name("name")
                .description("asdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfa")
                .releaseDate(LocalDate.of(1996, 12, 4))
                .duration(Duration.ofHours(2))
                .build();

        Film result = controller.create(film);

        assertNull(result);
    }

    @Test
    public void shouldReturnNullWhenReleaseDateIsNotValid() {
        Film film = Film.builder()
                .id(0)
                .name("name")
                .description("asdf")
                .releaseDate(LocalDate.of(1895, 12, 27))
                .duration(Duration.ofHours(2))
                .build();

        Film result = controller.create(film);

        assertNull(result);
    }

    @Test
    public void shouldReturnNullWhenDurationIsNegative() {
        Film film = Film.builder()
                .id(0)
                .name("name")
                .description("asdf")
                .releaseDate(LocalDate.of(1996, 12, 4))
                .duration(Duration.ofHours(-2))
                .build();

        Film result = controller.create(film);

        assertNull(result);
    }
}
