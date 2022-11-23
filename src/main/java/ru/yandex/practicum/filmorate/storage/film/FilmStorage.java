package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.List;

public interface FilmStorage {
    Collection<Film> getAll();
    Film getById(int id);
    Film create(Film film);
    Film update(Film film);
    Film delete(Film film);
    Film addLike(int filmId, int userId);
    Film deleteLike(int filmId, int userId);
    Collection<Film> getTopLiked(int count);
}
