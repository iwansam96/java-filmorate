package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.util.IdGenerator;

import java.util.*;
import java.util.stream.Collectors;

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

    @Override
    public Film addLike(int filmId, int userId) {
        Film film = this.getById(filmId);
        Set<Integer> likes = film.getLikes();
        boolean result = likes.add(userId);
        if (!result)
            return null;
        else
            film.setLikes(likes);
        return film;
    }

    @Override
    public Film deleteLike(int filmId, int userId) {
        Film film = this.getById(filmId);
        Set<Integer> likes = film.getLikes();
        if (likes.contains(userId)) {
            likes.remove(userId);
        }
        else
            return null;
        return film;
    }

    @Override
    public List<Film> getTopLiked(int count) {
        return this.getAll().stream()
                .sorted((f1, f2) -> f2.getLikes().size()-f1.getLikes().size())
                .limit(count)
                .collect(Collectors.toList());
    }
}
