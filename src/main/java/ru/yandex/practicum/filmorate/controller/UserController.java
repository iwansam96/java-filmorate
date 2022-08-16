package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class UserController {
    private Map<Integer, User> users = new HashMap<>();

    @GetMapping("/users")
    public Map<Integer, User> getAll() {
        return users;
    }

    @PostMapping("/users")
    public User create(@Valid @RequestBody User user) {
        log.info("Получен запрос к эндпоинту: POST /users");
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping("/users")
    public User update(@Valid @RequestBody User user) {
        log.info("Получен запрос к эндпоинту: PUT /users");
            if (users.containsKey(user.getId()))
                users.put(user.getId(), user);
            else {
                log.warn("Id not found");
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        return user;
    }
}
