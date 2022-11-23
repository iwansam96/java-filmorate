package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;

import javax.validation.Valid;
import java.util.Collection;

@Slf4j
@RestController
public class UserController {
    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public Collection<User> getAll() {
        log.info("Получен запрос к эндпоинту: GET /users");
        return service.getAll();
    }

    @GetMapping("/users/{id}")
    public User getById(@PathVariable int id) {
        log.info("Получен запрос к эндпоинту: GET /users/{id}");
        User result = service.getById(id);
        if (result == null) {
            log.warn("User id {} not found", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return result;
    }

    @PostMapping("/users")
    public User create(@Valid @RequestBody User user) {
        log.info("Получен запрос к эндпоинту: POST /users");
        return service.create(user);
    }

    @PutMapping("/users")
    public User update(@Valid @RequestBody User user) {
        log.info("Получен запрос к эндпоинту: PUT /users");
        User result = service.update(user);
        if (result == null) {
            log.warn("Id not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return user;
    }

    @DeleteMapping("/users")
    public User delete(@RequestBody User user) {
        log.info("Получен запрос к эндпоинту: DELETE /users");
        User result = service.delete(user);
        if (result == null) {
            log.warn("User with id {} to REMOVE not found", user.getId());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return user;
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public User addFriend(@PathVariable int id, @PathVariable int friendId) {
        log.info("Получен запрос к эндпоинту: PUT /users/{}/friends/{}", id, friendId);
        User result = service.addFriend(id, friendId);
        if (result == null) {
            log.warn("User with id {} or {} to ADD FRIEND not found", id, friendId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return result;
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public User deleteFriend(@PathVariable int id, @PathVariable int friendId) {
        log.info("Получен запрос к эндпоинту: DELETE /users/{}/friends/{}", id, friendId);
        User result = service.deleteFriend(id, friendId);
        if (result == null) {
            log.warn("User with id {} or {} to REMOVE FRIEND not found", id, friendId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return result;
    }

    @GetMapping("/users/{id}/friends")
    public Collection<User> getAllFriends(@PathVariable int id) {
        log.info("Получен запрос к эндпоинту: GET /users/{}/friends/", id);
        Collection<User> result = service.getALLFriends(id);
        if (result == null) {
            log.warn("User with id {} to GET ALL FRIENDS not found", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return result;
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public Collection<User> getMutualFriends(@PathVariable int id, @PathVariable int otherId) {
        log.info("Получен запрос к эндпоинту: GET /users/{}/friends/common/{}", id, otherId);
        Collection<User> result = service.getMutualFriends(id, otherId);
        if (result == null) {
            log.warn("User with id {} or {} to GET MUTUAL FRIENDS not found", id, otherId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return result;
    }
}
