package ru.yandex.practicum.filmorate.service.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.like.LikeStorage;

import java.util.Collection;
import java.util.Optional;

@Service
public class FilmService {

    private final FilmStorage storage;
    private final LikeStorage likeStorage;

    @Autowired
    public FilmService(FilmStorage storage, LikeStorage likeStorage) {
        this.storage = storage;
        this.likeStorage = likeStorage;
    }


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


    public Integer addLike(int filmId, int userId) {
        return likeStorage.addLike(filmId, userId);
    }

    public Integer deleteLike(int filmId, int userId) {
        return likeStorage.deleteLike(filmId, userId);
    }

    public Collection<Film> getTopLiked(int count) {
        return storage.getTopLiked(count);
    }
}
