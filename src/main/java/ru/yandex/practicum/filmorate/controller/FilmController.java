package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
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
//        try {
//            validate(film);
            films.put(film.getId(), film);
//        }
//        catch (ValidationException e) {
//            log.warn(e.getMessage());
//        }
        return film;
    }

    @PutMapping("/films")
    public Film update(@Valid @RequestBody Film film) {
        log.info("Получен запрос к эндпоинту: PUT /films");
//        try {
//            if (validate(film) != null && films.containsKey(film.getId()))
                films.put(film.getId(), film);
//            else {
//                log.warn("Id not found");
//            }
//        } catch (ValidationException e) {
//            log.warn(e.getMessage());
//        }
        return film;
    }

//    private Film validate(Film film) throws ValidationException {
//        if (film.getName().isBlank()) {
//            throw new ValidationException("Name is not valid: cannot be blank");
//        }
//        if (film.getDescription().length() > 200) {
//            throw new ValidationException("Description is not valid: max 200 symbols");
//        }
//        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
//            throw new ValidationException("Release date is not valid: cannot be before then 28.12.1895");
//        }
//        if (film.getDuration().isNegative()) {
//            throw new ValidationException("Duration is not valid: cannot be negative");
//        }
//        return film;
//    }
}
