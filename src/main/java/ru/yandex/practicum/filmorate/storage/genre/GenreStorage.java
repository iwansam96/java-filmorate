package ru.yandex.practicum.filmorate.storage.genre;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;

public interface GenreStorage {
    public Collection<Genre> getAll();
    public Genre getById(int id);
    public Collection<Genre> getGenresByFilmId(int filmId);
}
