package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.util.IdGenerator;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class InMemoryUserStorage implements UserStorage{
    private Map<Integer, User> users = new HashMap<>();

    @Override
    public Collection<User> getAll() {
        return users.values();
    }

    @Override
    public User getById(int id) {
        return users.get(id);
    }

    @Override
    public User create(User user) {
        user.setId(IdGenerator.generateId("user"));
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User update(User user) {
        if (users.containsKey(user.getId()))
            users.put(user.getId(), user);
        else
            return null;
        return user;
    }

    @Override
    public User delete(User user) {
        if (users.containsKey(user.getId()))
            users.remove(user.getId());
        else
            return null;
        return user;
    }
}
