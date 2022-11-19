package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.genre.GenreService;

import java.util.Collection;

@Slf4j
@RestController
public class GenreController {

    private final GenreService service;

    @Autowired
    public GenreController(GenreService service) {
        this.service = service;
    }

    @GetMapping("/genres")
    public Collection<Genre> getAll() {
        return service.getAll();
    }

    @GetMapping("/genres/{id}")
    public Genre getById(@PathVariable int id) {
        Genre result = service.getById(id);
        if (result == null) {
            log.warn("Genre id {} not found", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return result;
    }
}
