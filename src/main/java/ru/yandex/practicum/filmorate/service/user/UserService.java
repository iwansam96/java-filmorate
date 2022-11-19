package ru.yandex.practicum.filmorate.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserStorage storage;

    @Autowired
    public UserService (UserStorage storage) {
        this.storage = storage;
    }

//    Basic functions
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


    //    Business functions
    public User addFriend(int userId, int friendId) {
        User user = this.getById(userId);
        User friend = this.getById(friendId);

        if (user == null || friend == null)
            return null;
        user.getFriendsUnconfirmed().add(friend.getId());
        friend.getFriendsUnconfirmed().add(userId);
        storage.update(user);
        return user;
    }

    public User deleteFriend(int userId, int friendId) {
        User user = this.getById(userId);
        User friend = this.getById(friendId);

        Set<Integer> userFriends = user.getFriendsUnconfirmed();

        if (userFriends.contains(friendId)) {
            userFriends.remove(friendId);
            user.setFriendsUnconfirmed(userFriends);
        }
        return friend;
    }

    public Collection<User> getALLFriends(int id) {
        return this.getById(id).getFriendsUnconfirmed().stream().map(this::getById).collect(Collectors.toList());
    }

    public List<User> getMutualFriends(int userId, int secondUserId) {
        Set<Integer> userFriends = this.getById(userId).getFriendsUnconfirmed();
        Set<Integer> secondUserFriends = this.getById(secondUserId).getFriendsUnconfirmed();
        if (userFriends == null || secondUserFriends == null)
            return null;
        return userFriends.stream()
                .filter(secondUserFriends::contains)
                .map(this::getById)
                .collect(Collectors.toUnmodifiableList());
    }
}
