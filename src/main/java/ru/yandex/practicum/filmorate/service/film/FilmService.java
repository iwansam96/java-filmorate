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
        Film film = this.getById(filmId);
        Set<Integer> likes = film.getLikes();
        boolean result = likes.add(userId);
        if (!result)
            return null;
        else
            film.setLikes(likes);
        return film;
    }

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

    public List<Film> getTopLiked(int count) {
        return storage.getAll().stream()
                .sorted((f1, f2) -> f2.getLikes().size()-f1.getLikes().size())
                .limit(count)
                .collect(Collectors.toList());
    }
}
