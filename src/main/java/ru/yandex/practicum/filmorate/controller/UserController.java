package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
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
//        try {
//            if (validate(user) != null) {
                users.put(user.getId(), user);
//            }
//        } catch (ValidationException e) {
//            log.warn(e.getMessage());
//        }
        return user;
    }

    @PutMapping("/users")
    public User update(@Valid @RequestBody User user) {
        log.info("Получен запрос к эндпоинту: PUT /users");
//        try {
//            if (validate(user) != null & users.containsKey(user.getId()))
                users.put(user.getId(), user);
//            else {
//                log.warn("Id not found");
//            }
//        } catch (ValidationException e) {
//            log.warn(e.getMessage());
//        }
        return user;
    }

//    private User validate(User user) throws ValidationException {
//        if (user.getEmail().isBlank() || !user.getEmail().contains("@"))
//            throw new ValidationException("Email is not valid");
//        if (user.getLogin().isBlank() || user.getLogin().contains(" "))
//            throw new ValidationException("Name is not valid");
//        if (user.getName().isBlank())
//            user.setName(user.getLogin());
//        if (user.getBirthday().isAfter(LocalDate.now()))
//            throw new ValidationException("Birthday cannot be in future");
//        return user;
//    }
}
