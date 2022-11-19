package ru.yandex.practicum.filmorate.storage.user;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;

import java.time.LocalDate;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_= @Autowired)
@Sql("/testSchema.sql")
@Sql("/testdata.sql")
public class UserStorageTest {
    private final UserDbStorage userDbStorage;

    @Test
    public void shouldReturnAllUsersWhenGetAllUsers() {
        Collection<User> allUsers = userDbStorage.getAll();

        assertEquals(5, allUsers.size());
    }

    @Test
    public void shouldReturnAllUsersWithNewUser() {
        User user = new User(-1, "newUser@mailcat.net", "newUserLogin", "NewUserName",
                LocalDate.of(1991, 11, 11));
        User result = userDbStorage.create(user);
        assertTrue(userDbStorage.getAll().contains(result));
//        assertEquals(user, result);
    }

    @Test
    public void shouldReturnAllUsersWithUser2WithNewLogin() {
        User user = null;
        String newLogin = "updated_login";
        var users = userDbStorage.getAll();
        for (User u : users) {
            if (u.getLogin().equals("user2"))
                user = u;
        }
        if (user != null)
            user.setLogin(newLogin);
        User updatedUser = userDbStorage.update(user);

        assertEquals(newLogin, updatedUser.getLogin());
    }

    @Test
    public void shouldReturnAllUsersWithoutFirst() {
        Collection<User> allUsers = userDbStorage.getAll();
        var userToDelete = (User) allUsers.toArray()[0];
        System.out.println(userToDelete);
        userDbStorage.delete(userToDelete);
        allUsers = userDbStorage.getAll();

        assertEquals(4, allUsers.size());
    }
}
