package ru.yandex.practicum.filmorate.service.genre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;

import java.util.Collection;

@Service
public class GenreService {

    private final GenreStorage storage;

    @Autowired
    public GenreService (GenreStorage storage) {
        this.storage = storage;
    }

    public Collection<Genre> getAll() {
        return storage.getAll();
    }

    public Genre getById(Integer id) {
        return storage.getById(id);
    }
}
