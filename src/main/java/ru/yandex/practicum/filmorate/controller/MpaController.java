package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.mpa.MpaService;

import java.util.Collection;

@Slf4j
@RestController
public class MpaController {

    private final MpaService service;

    @Autowired
    public MpaController(MpaService service) {
        this.service = service;
    }

    @GetMapping("/mpa")
    public Collection<Mpa> getAll() {
        return service.getAll();
    }

    @GetMapping("/mpa/{id}")
    public Mpa getById(@PathVariable int id) {
        Mpa result = service.getById(id);
        if (result == null) {
            log.warn("Mpa id {} not found", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return result;
    }
}
