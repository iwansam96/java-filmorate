package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_= @Autowired)
@Sql("/testSchema.sql")
@Sql("/data.sql")
@Sql("/testdata.sql")
public class FilmStorageTest {
    private final FilmDbStorage filmDbStorage;

    @Test
    public void shouldReturnAllFilmsWhenGetAllFilms() {
        Collection<Film> allFilms = filmDbStorage.getAll();

        assertEquals(2, allFilms.size());
    }

    @Test
    public void shouldReturnAllFilmsWithNewFilm() {
        Film film = new Film(-1, "newFilm", "newFilm Description", LocalDate.of(2002, 02, 02),
                256, new HashSet<>());
        film.setMpa(new Mpa(1, "testMpa"));
        Film result = filmDbStorage.create(film);
        assertTrue(filmDbStorage.getAll().contains(result));
    }

    @Test
    public void shouldReturnAllFilmsWithFilm2WithNewDescription() {
        Film film = null;
        String newDescription = "Updated description";
        var films = filmDbStorage.getAll();
        for (Film f : films) {
            if (f.getName().equals("film2"))
                film = f;
        }
        if (film != null) {
            film.setDescription(newDescription);
        }

        Film updatedFilm = filmDbStorage.update(film);
        assertEquals(newDescription, updatedFilm.getDescription());
    }

    @Test
    public void shouldReturnAllFilmsWithoutFirst() {
        Collection<Film> allFilms = filmDbStorage.getAll();
        filmDbStorage.delete((Film) allFilms.toArray()[0]);
        allFilms = filmDbStorage.getAll();

        assertEquals(1, allFilms.size());
    }
}
