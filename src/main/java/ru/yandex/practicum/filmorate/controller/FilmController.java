package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class FilmController {
    private Map<Integer, Film> films = new HashMap<>();

    @GetMapping("/films")
    public Map<Integer, Film> getAll() {
        return films;
    }

    @PostMapping("/films")
    public Film create(@Valid @RequestBody Film film) {
        log.info("Получен запрос к эндпоинту: POST /films");
        films.put(film.getId(), film);
        return film;
    }

    @PutMapping("/films")
    public Film update(@Valid @RequestBody Film film) {
        log.info("Получен запрос к эндпоинту: PUT /films");
            if (films.containsKey(film.getId()))
                films.put(film.getId(), film);
            else {
                log.warn("Id not found");
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        return film;
    }
}
