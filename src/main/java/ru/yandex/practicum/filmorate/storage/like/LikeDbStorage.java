package ru.yandex.practicum.filmorate.storage.like;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

@Component
public class LikeDbStorage implements LikeStorage {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LikeDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Integer addLike(int filmId, int userId) {
        String sql = "insert into LIKES (FILM_ID, USER_ID) values ( ?, ? )";

        Integer linesAdded = jdbcTemplate.update(sql, filmId, userId);

        return linesAdded;
    }

    @Override
    public Integer deleteLike(int filmId, int userId) {
        String sql = "delete from LIKES where FILM_ID = ? and USER_ID = ?";

        int linesDeleted = jdbcTemplate.update(sql, filmId, userId);
        if (linesDeleted < 1)
            return null;

        return linesDeleted;
    }

    public Set<Integer> getLikesByFilmId(int id) {
        String sql = "select * from LIKES where FILM_ID = ?";
        var likes = jdbcTemplate.query(sql, this::makeLike, id);
        if (likes.isEmpty() || likes.get(0) == null)
            return null;
        return new HashSet<>(likes);
    }

    private Integer makeLike(ResultSet rs, int rowNum) throws SQLException {
        if (rowNum < 0)
            return null;
        return rs.getInt("USER_ID");
    }
}
