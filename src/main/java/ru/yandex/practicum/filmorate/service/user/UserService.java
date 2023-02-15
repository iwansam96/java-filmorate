package ru.yandex.practicum.filmorate.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.friend.FriendStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;

@Service
public class UserService {
    private final UserStorage storage;
    private final FriendStorage friendStorage;

    @Autowired
    public UserService (UserStorage storage, FriendStorage friendStorage) {
        this.storage = storage;
        this.friendStorage = friendStorage;
    }


    public Collection<User> getAll() {
        return storage.getAll();
    }

    public User getById(Integer id) {
        return storage.getById(id);
    }

    public User create(User user) {
        return storage.create(user);
    }

    public User update(User user) {
        return storage.update(user);
    }

    public User delete(User user) {
        return storage.delete(user);
    }



    public User addFriend(int userId, int friendId) {
        return this.friendStorage.addFriend(userId, friendId);
    }

    public User deleteFriend(int userId, int friendId) {
        return this.friendStorage.deleteFriend(userId, friendId);
    }

    public Collection<User> getALLFriends(int id) {
        return this.friendStorage.getALLFriends(id);
    }

    public Collection<User> getMutualFriends(int userId, int secondUserId) {
        return this.friendStorage.getMutualFriends(userId, secondUserId);
    }
}
