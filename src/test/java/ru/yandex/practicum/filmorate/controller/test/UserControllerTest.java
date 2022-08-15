package ru.yandex.practicum.filmorate.controller.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest {
    UserController controller;

    @BeforeEach
    public void createFilmController() {
        controller = new UserController();
    }

    @Test
    public void shouldReturnNullWhenEmailIsNotValid() {
        User user = User.builder()
            .id(0)
            .email("email.com")
            .login("login")
            .name("name")
            .birthday(LocalDate.of(1996, 12, 4))
            .build();

        User result = controller.create(user);

        assertNull(result);
    }

    @Test
    public void shouldReturnNullWhenEmailIsBlank() {
        User user = User.builder()
                .id(0)
                .email("")
                .login("login")
                .name("name")
                .birthday(LocalDate.of(1996, 12, 4))
                .build();

        User result = controller.create(user);

        assertNull(result);
    }

    @Test
    public void shouldReturnNullWhenLoginIsNotValid() {
        User user = User.builder()
                .id(0)
                .email("valid@email.com")
                .login("log in")
                .name("name")
                .birthday(LocalDate.of(1996, 12, 4))
                .build();

        User result = controller.create(user);

        assertNull(result);
    }

    @Test
    public void shouldReturnNullWhenLoginIsBlank() {
        User user = User.builder()
                .id(0)
                .email("valid@email.com")
                .login("")
                .name("name")
                .birthday(LocalDate.of(1996, 12, 4))
                .build();

        User result = controller.create(user);

        assertNull(result);
    }

    @Test
    public void shouldReturnUserWithNameEqualsLoginWhenNameIsBlank() {
        User user = User.builder()
                .id(0)
                .email("valid@email.com")
                .login("login")
                .name("")
                .birthday(LocalDate.of(1996, 12, 4))
                .build();

        User result = controller.create(user);

        assertEquals(result.getName(), result.getLogin());
    }

    @Test
    public void shouldReturnNullWhenBirthdayIsNotValid() {
        User user = User.builder()
                .id(0)
                .email("valid@email.com")
                .login("login")
                .name("name")
                .birthday(LocalDate.now().plusDays(1))
                .build();

        User result = controller.create(user);

        assertNull(result);
    }
}
