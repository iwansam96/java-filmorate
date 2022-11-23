package ru.yandex.practicum.filmorate.service.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private final FilmStorage storage;

    @Autowired
    public FilmService(FilmStorage storage) {
        this.storage = storage;
    }


    //    Basic functions
    public Collection<Film> getAll() {
        return storage.getAll();
    }

    public Film getById(int id) {
        Optional<Film> film = storage.getAll().stream().filter(f -> f.getId() == id).findFirst();
        return film.orElse(null);
    }

    public Film create(Film film) {
        return storage.create(film);
    }

    public Film update(Film film) {
        return storage.update(film);
    }

    public Film delete(Film film) {
        return storage.delete(film);
    }

    //    Business functions
    public Film addLike(int filmId, int userId) {
        return storage.addLike(filmId, userId);
    }

    public Film deleteLike(int filmId, int userId) {
        return storage.deleteLike(filmId, userId);
    }

    public Collection<Film> getTopLiked(int count) {
        return storage.getTopLiked(count);
    }
}
