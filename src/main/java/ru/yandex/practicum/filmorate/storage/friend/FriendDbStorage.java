package ru.yandex.practicum.filmorate.storage.friend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collection;

@Component
public class FriendDbStorage implements FriendStorage {

    private final JdbcTemplate jdbcTemplate;
    private final UserStorage userStorage;

    @Autowired
    public FriendDbStorage(JdbcTemplate jdbcTemplate, UserStorage userStorage) {
        this.jdbcTemplate = jdbcTemplate;
        this.userStorage = userStorage;
    }

    @Override
    public User addFriend(int userId, int friendId) {

        User user = userStorage.getById(userId);
        User friend = userStorage.getById(friendId);
        if(user == null || friend == null)
            return null;

        String sql = "insert into FRIENDS (USER_ID, FRIEND_ID, IS_CONFIRMED) values ( ?, ?, ? )";
        int addedLines = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, friendId);
            ps.setBoolean(3, false);

            return ps;
        });

        if (addedLines < 0)
            return null;
        return user;
    }

    @Override
    public User deleteFriend(int userId, int friendId) {
        String sql = "delete from FRIENDS where USER_ID = ? and FRIEND_ID = ?";

        int deletedLines = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, userId);
            ps.setInt(2, friendId);

            return ps;
        });

        if (deletedLines < 0)
            return null;

        return userStorage.getById(userId);
    }

    @Override
    public Collection<User> getALLFriends(int id) {
        Collection<User> friends = new ArrayList<>();
        String sql = "select FRIEND_ID from FRIENDS where USER_ID = ?";

        var friendIds = jdbcTemplate.queryForList(sql, Integer.class, id);
        for (Integer friendId : friendIds) {
            User friend = userStorage.getById(friendId);
            if (friend != null)
                friends.add(friend);
        }

        return friends;
    }

    @Override
    public Collection<User> getMutualFriends(int userId, int secondUserId) {
        Collection<User> mutualFriends = new ArrayList<>();
        String sql = "select FRIEND_ID from FRIENDS where USER_ID = ?" +
                "intersect select FRIEND_ID from FRIENDS where USER_ID = ?";

        var mutualFriendIds = jdbcTemplate.queryForList(sql, Integer.class, userId, secondUserId);
        for (Integer mutualFriendId : mutualFriendIds) {
            User mutualFriend = userStorage.getById(mutualFriendId);
            if (mutualFriend != null)
                mutualFriends.add(mutualFriend);
        }

        return mutualFriends;
    }
}
