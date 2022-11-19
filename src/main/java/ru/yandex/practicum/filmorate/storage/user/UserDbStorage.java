package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Component
@Primary
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Collection<User> getAll() {
        String sql = "select * from USERS";

        return jdbcTemplate.query(sql, this::makeUser);
    }

    @Override
    public User getById(int id) {
        String sql = "select * from USERS where USER_ID = ?";

        List<User> users = jdbcTemplate.query(sql, this::makeUser, id);
        if (!users.isEmpty() && users.get(0) != null) {
            return users.get(0);
        } else {
            return null;
        }
    }

    @Override
    public User create(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sqlInsert = "insert into USERS (EMAIL, LOGIN, USERNAME, BIRTHDAY)" +
                "values (?, ?, ?, ?)";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlInsert, new String[] {"USER_ID"});

            String email = user.getEmail();
            String login = user.getLogin();
            String username = user.getName();
            LocalDate birthday = user.getBirthday();

            ps.setString(1, email);
            ps.setString(2, login);
            ps.setString(3, username);
            ps.setDate(4, java.sql.Date.valueOf(birthday));

            return ps;
        }, keyHolder);

        int id = -1;
        Number keyId = keyHolder.getKey();
        if (keyId != null)
            id = keyId.intValue();

        return this.getById(id);
    }

    @Override
    public User update(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sqlInsert = "update USERS set EMAIL = ?, LOGIN = ?, USERNAME = ?, BIRTHDAY = ? where USER_ID = ?";

        if (isUserWithIdPresent(user.getId())) {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sqlInsert, new String[] {"USER_ID"});

                String email = user.getEmail();
                String login = user.getLogin();
                String username = user.getName();
                LocalDate birthday = user.getBirthday();
                int updateUserId = user.getId();

                ps.setString(1, email);
                ps.setString(2, login);
                ps.setString(3, username);
                ps.setDate(4, java.sql.Date.valueOf(birthday));
                ps.setInt(5, updateUserId);

                return ps;
            }, keyHolder);
        }

        int id = -1;
        Number keyId = keyHolder.getKey();
        if (keyId != null)
            id = keyId.intValue();

        return this.getById(id);
    }

    @Override
    public User delete(User user) {
        if (isUserWithIdPresent(user.getId())) {
            String sql = "delete from USERS where USER_ID = ?";

            jdbcTemplate.update(sql, user.getId());
            return user;
        }
        return null;
    }

    private User makeUser(ResultSet rs, int rowNum) throws SQLException {
        if (rowNum < 0)
            return null;
        Integer id = rs.getInt("USER_ID");
        String email = rs.getString("EMAIL");
        String login = rs.getString("LOGIN");
        String name = rs.getString("USERNAME");
        LocalDate birthday = rs.getDate("BIRTHDAY").toLocalDate();

        return new User(id, email, login, name, birthday);
    }

    private boolean isUserWithIdPresent(int id) {
        return this.getById(id) != null;
    }
}
