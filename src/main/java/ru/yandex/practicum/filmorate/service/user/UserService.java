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
//        User user = this.getById(userId);
//        User friend = this.getById(friendId);
//
//        if (user == null || friend == null)
//            return null;
//        user.getFriendsUnconfirmed().add(friend.getId());
//        friend.getFriendsUnconfirmed().add(userId);
//        storage.update(user);
//        return user;
        return this.friendStorage.addFriend(userId, friendId);
    }

    public User deleteFriend(int userId, int friendId) {
//        User user = this.getById(userId);
//        User friend = this.getById(friendId);
//
//        Set<Integer> userFriends = user.getFriendsUnconfirmed();
//
//        if (userFriends.contains(friendId)) {
//            userFriends.remove(friendId);
//            user.setFriendsUnconfirmed(userFriends);
//        }
//        return friend;
        return this.friendStorage.deleteFriend(userId, friendId);
    }

    public Collection<User> getALLFriends(int id) {
        return this.friendStorage.getALLFriends(id);
//        return this.getById(id).getFriendsUnconfirmed().stream().map(this::getById).collect(Collectors.toList());
    }

    public Collection<User> getMutualFriends(int userId, int secondUserId) {
//        Set<Integer> userFriends = this.getById(userId).getFriendsUnconfirmed();
//        Set<Integer> secondUserFriends = this.getById(secondUserId).getFriendsUnconfirmed();
//        if (userFriends == null || secondUserFriends == null)
//            return null;
//        return userFriends.stream()
//                .filter(secondUserFriends::contains)
//                .map(this::getById)
//                .collect(Collectors.toUnmodifiableList());
        return this.friendStorage.getMutualFriends(userId, secondUserId);
    }
}
