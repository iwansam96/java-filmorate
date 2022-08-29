package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
public class FilmController {

    private final FilmService service;

    @Autowired
    public FilmController(FilmService service) {
        this.service = service;
    }

    @GetMapping("/films")
    public Collection<Film> getAll() {
        return service.getAll();
    }

    @GetMapping("/films/{id}")
    public Film getById(@PathVariable int id) {
        Film result = service.getById(id);
        if (result == null) {
            log.warn("Film id {} not found", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return result;
    }

    @PostMapping("/films")
    public Film create(@Valid @RequestBody Film film) {
        log.info("Получен запрос к эндпоинту: POST /films");
        service.create(film);
        return film;
    }

    @PutMapping("/films")
    public Film update(@Valid @RequestBody Film film) {
        log.info("Получен запрос к эндпоинту: PUT /films");
        Film result = service.update(film);
        if (result == null) {
            log.warn("Film with id {} to UPDATE not found", film.getId());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return film;
    }

    @DeleteMapping("/films")
    public Film delete(@RequestBody Film film) {
        log.info("Получен запрос к эндпоинту: DELETE /films");
        Film result = service.delete(film);
        if (result == null) {
            log.warn("Film with id {} to DELETE not found", film.getId());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return film;
    }

    @PutMapping("/films/{id}/like/{userId}")
    public Film addLike(@PathVariable int id, @PathVariable int userId) {
        log.info("Получен запрос к эндпоинту: PUT /films/{}/like/{}", id, userId);
        Film result = service.addLike(id, userId);
        if (result == null) {
            log.warn("Film with id {} or like from user with id {} to PUT LIKE not found", id, userId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return result;
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public Film deleteLike(@PathVariable int id, @PathVariable int userId) {
        log.info("Получен запрос к эндпоинту: DELETE /films/{}/like/{}", id, userId);
        Film result = service.deleteLike(id, userId);
        if (result == null) {
            log.warn("Film with id {} or like from user with id {} to DELETE LIKE not found", id, userId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return result;
    }

    @GetMapping("/films/popular")
    public List<Film> getTopLiked(@RequestParam(value="count", required = false) Integer count) {
        log.info("Получен запрос к эндпоинту: GET /films/popular?count={}", count);
        int size = count == null ? 10 : count;
        return service.getTopLiked(size);
    }
}
