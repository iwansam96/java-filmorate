package ru.yandex.practicum.filmorate.storage.friend;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.List;

public interface FriendStorage {
    User addFriend(int userId, int friendId);
    User deleteFriend(int userId, int friendId);
    Collection<User> getALLFriends(int id);
    Collection<User> getMutualFriends(int userId, int secondUserId);
}
