package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.util.IdGenerator;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage{
    private Map<Integer, Film> films = new HashMap<>();

    @Override
    public Collection<Film> getAll() {
        return films.values();
    }

    @Override
    public Film getById(int id) {
        return films.get(id);
    }

    @Override
    public Film create(Film film) {
        film.setId(IdGenerator.generateId("film"));
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film update(Film film) {
        if (films.containsKey(film.getId()))
            films.put(film.getId(), film);
        else {
            return null;
        }
        return film;
    }

    @Override
    public Film delete(Film film) {
        if (films.containsKey(film.getId()))
            films.remove(film.getId());
        else
            return null;
        return film;
    }
}
